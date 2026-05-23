Spring Boot REST API for habit tracking, completion stats, and daily discipline logic.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Maven
- JUnit
- Jakarta Validation
- REST API

## Features

- Get all habits
- Get habit by ID
- Add new habit
- Update habit
- Mark habit as completed
- Delete habit
- Validate request data
- Calculate completion statistics
- Classify day type: Perfect, Strong, System, Recovery, Zero

## API Endpoints

| Method | Endpoint | Description |
|---|---|---|
| GET | `/habits` | Get all habits |
| GET | `/habits/{id}` | Get habit by ID |
| POST | `/habits` | Create habit |
| PUT | `/habits/{id}` | Update habit |
| PATCH | `/habits/{id}/complete` | Mark habit completed |
| DELETE | `/habits/{id}` | Delete habit |
| GET | `/habits/stats` | Get habit statistics |
| GET | `/habits/search?name=Code` | Search habit by name |
| GET | `/habits/priority/{priority}` | Filter habits by priority |

## Example: Create Habit

### Request

`POST /habits`

```json
{
  "name": "Read",
  "completed": false,
  "priority": "Medium"
}
```

### Response

```json
{
  "id": 4,
  "name": "Read",
  "completed": false,
  "priority": "Medium"
}
```

## Example: Get Habit by ID

### Request

`GET /habits/1`

### Response

```json
{
  "id": 1,
  "name": "Code",
  "completed": true,
  "priority": "High"
}
```

## Example: Habit Stats

### Request

`GET /habits/stats`

### Response

```json
{
  "total": 3,
  "completed": 1,
  "notCompleted": 2,
  "percent": 33.333333333333336,
  "dayType": "Recovery day"
}
```