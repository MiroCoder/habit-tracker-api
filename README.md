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


## Web UI Preview

<img width="1122" height="916" alt="{8D2EEFFD-7253-40DA-89C7-C22FAD050D39}" src="https://github.com/user-attachments/assets/c48e4b9e-9c54-4342-a903-9654d6cfdd4f" />

<img width="905" height="846" alt="{80FF4793-7E26-4181-AD82-30BE14584D94}" src="https://github.com/user-attachments/assets/d260c504-0912-468c-88ff-533c48ba4d44" />


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

## Development Note

The HTML structure and API integration were implemented by me.

The visual CSS styling was AI-assisted, then reviewed and adapted by me.
