# API Endpoints Documentation

## 1. CalcController
**Base Path:** `/api/calc`

| Endpoint | Method | Input (Body) | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/insert` | POST | `CalcConfig[]` | `boolean` | Batch insert calculation configs |
| `/query` | GET/POST | (Empty) | `List<CalcConfig>` | Query all calculation configs |
| `/queryById` | POST | `Long` (id) | `CalcConfig` | Query config by ID |
| `/deleteById` | POST | `Long` (id) | `boolean` | Delete config by ID |
| `/update` | POST | `CalcConfig` | `boolean` | Update existing config |

### Data Structures
**CalcConfig**
- `id`: Long (Auto-generated)
- `key`: String
- `purpose`: String
- `value`: Double
- `description`: String

---

## 2. CalendarController
**Base Path:** `/api/calendar`

| Endpoint | Method | Input (Body) | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/add` | POST | `CalendarEvent` | `boolean` | Add a new calendar event |
| `/queryByMonth` | POST | `int` (month) | `List<CalendarEvent>` | Query events by month integer |
| `/queryByDateStr` | POST | `String` (dateStr) | `List<CalendarEvent>` | Query events by date string |
| `/queryEventsByRange` | POST | `CalendarEvent` (uses `start`, `end`) | `List<CalendarEvent>` | Query events within time range |
| `/delete` | POST | `long` (id) | `boolean` | Delete event by ID |

### Data Structures
**CalendarEvent**
- `id`: Long (Auto-generated)
- `title`: String
- `start`: Timestamp
- `startText`: String
- `end`: Timestamp
- `endText`: String
- `month`: int
- `dateStr`: String
- `logTime`: Timestamp

---

## 3. SettingController
**Base Path:** `/api/setting`

| Endpoint | Method | Input (Body) | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/del` | POST | `String` (target) | `boolean` | Delete all data for target. <br>Targets: `"deposit"`, `"calc"`, `"calendar"` |

---

## 4. TransLogController
**Base Path:** `/api/trans`

| Endpoint | Method | Input (Body) | Output | Description |
| :--- | :--- | :--- | :--- | :--- |
| `/save` | POST | `TransLog` | `boolean` | Save transaction log |
| `/queryByDateRange` | POST | `DateRange` | `List<TransLog>` | Query transactions by date range |
| `/delete` | POST | `long` (id) | `boolean` | Delete transaction by ID |

### Data Structures
**TransLog**
- `id`: Long (Auto-generated)
- `type`: String
- `category`: String
- `transDate`: Timestamp
- `name`: String
- `value`: Double
- `ps`: String (Note)
- `logTime`: Timestamp

**DateRange**
- `start`: Timestamp
- `end`: Timestamp
- `type`: String
- `keyword`: String
