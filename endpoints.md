# API Endpoints Documentation

> **Swagger UI**: 啟動應用後瀏覽 `http://localhost:9889/swagger-ui.html`
> **OpenAPI JSON**: `http://localhost:9889/v3/api-docs`

---

## 1. CalcController — 試算配置
**Base Path:** `/api/calc`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/insert` | POST | `CalcConfig[]` (Body) | `boolean` | 批次新增試算配置 |
| `/query` | GET | — | `List<CalcConfig>` | 查詢所有試算配置 |
| `/queryById` | POST | `Long` (Body) | `CalcConfig` | 依 ID 查詢試算配置 |
| `/delete/{id}` | DELETE | `{id}` (Path) | `boolean` | 刪除指定試算配置 |
| `/update` | PUT | `CalcConfig` (Body) | `boolean` | 更新試算配置 |

### CalcConfig
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `key` | String | 配置鍵名 |
| `purpose` | String | 用途說明 |
| `value` | Double | 配置數值 |
| `description` | String | 備註描述 |

---

## 2. CalendarController — 行事曆
**Base Path:** `/api/calendar`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/add` | POST | `CalendarEvent` (Body) | `boolean` | 新增行事曆事件 |
| `/queryByMonth` | POST | `int` (Body) | `List<CalendarEvent>` | 依月份查詢事件 |
| `/queryByDateStr` | POST | `String` (Body) | `List<CalendarEvent>` | 依日期字串查詢事件 |
| `/queryEventsByRange` | POST | `CalendarEvent` (Body, 使用 `start`/`end`) | `List<CalendarEvent>` | 依時間區間查詢事件 |
| `/delete/{id}` | DELETE | `{id}` (Path) | `boolean` | 刪除行事曆事件 |

### CalendarEvent
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `title` | String | 事件標題 |
| `start` | Timestamp | 開始時間 |
| `startText` | String | 開始時間文字 |
| `end` | Timestamp | 結束時間 |
| `endText` | String | 結束時間文字 |
| `month` | int | 月份 (1-12) |
| `dateStr` | String | 日期字串 |
| `logTime` | Timestamp | 紀錄時間 |

---

## 3. DiaryController — 日記
**Base Path:** `/api/diary`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/logs` | GET | `start`, `end` (Query, 毫秒, 選填) | `List<DiaryLog>` | 查詢日記 (可依區間篩選) |
| `/log` | POST | `DiaryLog` (Body) | `DiaryLog` | 新增/更新日記 |
| `/log/{id}` | DELETE | `{id}` (Path) | `void` | 刪除日記 |

### DiaryLog
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `title` | String | 日記標題 |
| `content` | String (TEXT) | 日記內容 |
| `transDate` | Timestamp | 日記日期 |
| `logTime` | Timestamp | 紀錄時間 |
| `mood` | String | 心情 (Emoji) |

---

## 4. ExerciseLogController — 運動紀錄
**Base Path:** `/api/exercise`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/save` | POST | `ExerciseLog` (Body) | `boolean` | 儲存運動紀錄 |
| `/queryByDateRange` | POST | `DateRange` (Body) | `List<ExerciseLog>` | 依日期區間查詢 |
| `/delete/{id}` | DELETE | `{id}` (Path) | `boolean` | 刪除運動紀錄 |

### ExerciseLog
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `exerciseName` | String | 運動名稱 |
| `duration` | Double | 時長 (分鐘) |
| `calories` | Double | 消耗卡路里 |
| `transDate` | Timestamp | 運動日期 |
| `ps` | String | 備註 |
| `logTime` | Timestamp | 紀錄時間 |

---

## 5. ExerciseTypeController — 運動類型
**Base Path:** `/api/exercise-type`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/all` | GET | — | `List<ExerciseType>` | 查詢所有運動類型 |
| `/save` | POST | `ExerciseType` (Body) | `ExerciseType` | 儲存運動類型 |
| `/delete/{id}` | DELETE | `{id}` (Path) | `boolean` | 刪除運動類型 |

### ExerciseType
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `name` | String | 運動名稱 |
| `icon` | String | 圖示 (Emoji) |
| `defaultDuration` | Double | 預設時長 (分鐘) |
| `kcalPerHour` | Double | 每小時消耗卡路里 |

---

## 6. MealController — 飲食管理
**Base Path:** `/api/meal`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/logs` | GET | `start`, `end` (Query, 毫秒, 選填) | `List<MealLog>` | 查詢飲食紀錄 |
| `/log` | POST | `MealLog` (Body) | `MealLog` | 新增/更新飲食紀錄 |
| `/log/{id}` | DELETE | `{id}` (Path) | `void` | 刪除飲食紀錄 |
| `/types` | GET | — | `List<MealType>` | 查詢所有飲食類型 |
| `/type` | POST | `MealType` (Body) | `MealType` | 儲存飲食類型 |
| `/type/{id}` | DELETE | `{id}` (Path) | `void` | 刪除飲食類型 |

### MealLog
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `mealName` | String | 餐點名稱 |
| `calories` | Double | 卡路里 |
| `transDate` | Timestamp | 用餐日期 |
| `ps` | String | 備註 |
| `logTime` | Timestamp | 紀錄時間 |

### MealType
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `name` | String | 類型名稱 |
| `icon` | String | 圖示 (Emoji) |
| `defaultCalories` | Double | 預設卡路里 |

---

## 7. TransLogController — 交易紀錄
**Base Path:** `/api/trans`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/save` | POST | `TransLog` (Body) | `boolean` | 儲存交易紀錄 |
| `/queryByDateRange` | POST | `DateRange` (Body) | `List<TransLog>` | 依日期區間查詢 |
| `/delete/{id}` | DELETE | `{id}` (Path) | `boolean` | 刪除交易紀錄 |

### TransLog
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `id` | Long | 主鍵 (自動產生) |
| `type` | String | 交易類型 (收入/支出) |
| `category` | String | 分類 |
| `transDate` | Timestamp | 交易日期 |
| `name` | String | 項目名稱 |
| `value` | Double | 金額 |
| `ps` | String | 備註 |
| `logTime` | Timestamp | 紀錄時間 |

### DateRange
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `start` | Timestamp | 起始時間 |
| `end` | Timestamp | 結束時間 |
| `type` | String | 類型篩選 (選填) |
| `keyword` | String | 關鍵字篩選 (選填) |

---

## 8. SettingController — 系統設定
**Base Path:** `/api/setting`

| Endpoint | Method | Input | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/del` | POST | `{"target": "..."}` (Body) | `boolean` | 清除指定模組資料 |
| `/export` | GET | — | `BackupData` | 匯出所有資料 |
| `/import` | POST | `BackupData` (Body) | `boolean` | 匯入還原資料 |
| `/app` | GET | — | `Map<String, String>` | 取得應用程式設定 |
| `/app` | POST | `AppSetting` (Body) | `AppSetting` | 儲存應用程式設定 |

### `/del` 可用的 target 值
`deposit`, `calc`, `calendar`, `exercise`, `exercisetype`, `meal`, `mealtype`

### AppSetting
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `keyName` | String | 設定鍵名 (主鍵) |
| `value` | String | 設定值 |

### BackupData
| 欄位 | 型別 | 說明 |
| :--- | :--- | :--- |
| `calcConfigs` | List\<CalcConfig\> | 試算配置列表 |
| `calendarEvents` | List\<CalendarEvent\> | 行事曆事件列表 |
| `transLogs` | List\<TransLog\> | 交易紀錄列表 |
| `exerciseLogs` | List\<ExerciseLog\> | 運動紀錄列表 |
| `exerciseTypes` | List\<ExerciseType\> | 運動類型列表 |
| `mealLogs` | List\<MealLog\> | 飲食紀錄列表 |
| `mealTypes` | List\<MealType\> | 飲食類型列表 |
| `appSettings` | List\<AppSetting\> | 應用程式設定列表 |
