# Personal Record Service Contract

## Purpose

This document defines the operational contract between a natural-language agent and Asset Manager. The primary rule is that analytics dimensions must remain canonical and stable; user-specific detail belongs in lower-level descriptive fields.

Backend: `http://localhost:9889`
OpenAPI: `/v3/api-docs`

## Global rules

- New records may be created directly when intent is clear.
- Updates must first identify the existing record/entity ID.
- Bulk delete, module wipe, backup import, or other destructive multi-record operations require explicit confirmation.
- Relative time is resolved in the user's local timezone before calling the API.
- Do not invent new analytics categories when a canonical taxonomy exists.
- `value` for transactions is always non-negative; `type` distinguishes income from expense.

## Analytics dimension hierarchy

| Record | Primary analytics dimension | Secondary dimension | Detail |
| --- | --- | --- | --- |
| Transaction | `category` | `name` | `ps` |
| Meal | `MealType` via `mealTypeId` | `mealName` | `ps` |
| Exercise | `ExerciseType` via `exerciseTypeId` | `exerciseName` | `ps` |
| CalcConfig | sign / `purpose` semantics | `description` | `value` |
| Diary | no fixed graph taxonomy | `title` | `content`, `mood` |
| Calendar | date/time | `title` | time fields |

Diary and Calendar intentionally do not have an artificial category taxonomy because the current frontend has no category-based graph for them.

# Transaction

Endpoints:
- `POST /api/trans/save`
- `POST /api/trans/queryByDateRange`
- `DELETE /api/trans/delete/{id}`

Required: `type`, `category`, `name`, `value`, `transDate`.

Canonical taxonomy:

Expense (`type=支出`):
- `食`
- `衣`
- `住`
- `行`
- `育`
- `樂`
- `其他`

Income (`type=收入`):
- `薪資`
- `投資`
- `其他`

The backend rejects non-canonical categories. `category` is the primary graph dimension, `name` is the concrete item, and `ps` is the narrowest drill-down.

Example: `午餐牛肉麵 180 元`

```json
{
  "type": "支出",
  "category": "食",
  "name": "牛肉麵",
  "value": 180,
  "ps": "午餐"
}
```

# Meal

Endpoints:
- `GET /api/meal/types`
- `GET /api/meal/logs`
- `POST /api/meal/log`
- `DELETE /api/meal/log/{id}`

Required: `mealName`, `transDate`.
Recommended for new records: `mealTypeId`, `calories`.

Canonical default MealType taxonomy:
- `早餐`
- `午餐`
- `晚餐`
- `消夜`
- `零食`
- `飲料`
- `其他`

`MealType` represents meal period only. It does not contain default calories. Calories belong to each `MealLog` because a meal period cannot provide a meaningful calorie estimate.

Graph hierarchy:
`MealType -> mealName -> ps`

Example: `午餐牛肉麵，大約 650 卡`

```json
{
  "mealTypeId": 2,
  "mealName": "牛肉麵",
  "calories": 650,
  "ps": ""
}
```

The actual `mealTypeId` must be resolved from `GET /api/meal/types`; never assume IDs across databases.

Migration rule: the backend may replace the legacy Rice/Noodles/Bread/... MealType taxonomy only when there are zero MealLog records. If MealLog records exist, legacy types are preserved to avoid breaking references.

# Exercise

Endpoints:
- `GET /api/exercise-type/all`
- `POST /api/exercise/save`
- `POST /api/exercise/queryByDateRange`
- `DELETE /api/exercise/delete/{id}`

Required: `exerciseName`, `duration`, `transDate`.
Recommended for new records: `exerciseTypeId`.

`ExerciseType` is the stable primary analytics dimension. Current defaults include Jogging, Cycling, Walking, Fitness, Yoga, Swimming, and Basketball, but callers must resolve the current list dynamically.

`exerciseName` describes the specific activity and must not be used to create a new primary graph dimension when an existing ExerciseType fits.

Graph hierarchy:
`ExerciseType -> exerciseName -> ps`

If calories are omitted and `exerciseTypeId` resolves to a type with `kcalPerHour`, backend derives:

`calories = kcalPerHour * duration / 60`

Example: `河堤慢跑 40 分鐘`

```json
{
  "exerciseTypeId": 1,
  "exerciseName": "河堤慢跑",
  "duration": 40,
  "ps": ""
}
```

The actual type ID must be resolved dynamically.

# Diary

Endpoints:
- `GET /api/diary/logs`
- `POST /api/diary/log`
- `DELETE /api/diary/log/{id}`

Required: `title`, `content`, `transDate`.
Optional: `mood`.

No fixed analytics taxonomy is imposed. If only content is provided, the agent may generate a short neutral title without changing the user's meaning.

# Calendar

Endpoints:
- `POST /api/calendar/add`
- `PUT /api/calendar/update/{id}`
- `POST /api/calendar/queryEventsByRange`
- `POST /api/calendar/queryByDateStr`
- `POST /api/calendar/queryByMonth`
- `DELETE /api/calendar/delete/{id}`

Required: `title`, `start`, `dateStr`.

Calendar analysis is time-based, so no event category is currently required. Update flow must query/identify the event first, then update by ID.

# CalcConfig

Endpoints:
- `GET /api/calc/query`
- `POST /api/calc/queryById`
- `POST /api/calc/insert`
- `PUT /api/calc/update`
- `DELETE /api/calc/delete/{id}`

Required: `key`, `value`.

`key` is frequency (`每日`, `每周`, `每月`). `purpose` is a controlled semantic grouping used by the frontend analysis; `description` identifies the concrete recurring item. Existing CalcConfig data is not automatically migrated because production already contains meaningful configuration records.

CalcConfig represents recurring assumptions/configuration, not actual transactions.

Example:
- `每月定期定額 2330 一萬元` -> CalcConfig.
- `今天買 2330 花一萬元` -> Transaction.

# Multi-record intent

One user statement can create multiple records when the domains are independent and the information is sufficient.

Example: `午餐牛肉麵 180 元，大約 650 卡`
- Transaction: `食 -> 牛肉麵 -> 午餐`
- Meal: `午餐 -> 牛肉麵 -> optional note`

Do not ask the user to repeat information already present in the same statement.

# Read-before-write

Read existing data/type tables first when:
- resolving `mealTypeId`;
- resolving `exerciseTypeId`;
- updating or deleting an existing record;
- the user refers to `上一筆`, `剛剛那筆`, `昨天那筆`, etc.;
- duplicate creation is plausible.

If several existing records are equally plausible and cannot be safely distinguished, ask the user to choose.

# API response expectation

Agent-facing create/update APIs should return the persisted entity including generated ID. The current contract expects entity responses for Meal, Diary, Exercise, Transaction, Calendar add, and Calendar update.
