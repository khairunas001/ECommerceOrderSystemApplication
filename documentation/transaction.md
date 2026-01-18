## TRANSACTION API Documentation

### 1. Create Transaction❌ ✅

**`POST /e-commerce/transactions`**

📥 Request Body:

```json
{
  "product_id": "parfum",
  "quantity": 2
}
```

📄 Response (201 Created):

```json
{
  "status": "201 CREATED",
  "data": {
    "product_id": "parfum",
    "quantity": 2
  }
}

```

### 2. Gel All Transaction❌ ✅

**`GET /e-commerce/transactions`**

📄 Response (200 OK):

```json
{
  "status": "200 OK",
  "data": [
    {
      "id": "0d55004c-0e75-41e1-9771-782272daa73c",
      "product": {
        "id": "e6f59424-7a94-4f62-aa8c-c3fa3bc69ec0",
        "name": "Kain",
        "price": 200000,
        "stock": 71
      },
      "quantity": 5,
      "createdAt": "2026-01-16T15:48:07"
    },
    {
      "id": "b924d9e1-c8f4-4028-b08f-50559528153f",
      "product": {
        "id": "e6f59424-7a94-4f62-aa8c-c3fa3bc69ec0",
        "name": "Kain",
        "price": 200000,
        "stock": 71
      },
      "quantity": 12,
      "createdAt": "2026-01-16T16:49:48"
    },
    {
      "id": "df4d6c70-b110-48e6-8cc7-24220b8dbf80",
      "product": {
        "id": "e6f59424-7a94-4f62-aa8c-c3fa3bc69ec0",
        "name": "Kain",
        "price": 200000,
        "stock": 71
      },
      "quantity": 12,
      "createdAt": "2026-01-16T16:51:31"
    }
  ]
}
```

### 3. Get Transaction❌ ✅

**`GET /e-commerce/transactions/{transaction_id}`**

📄 Response (200 OK):

```json
{
  "status": "200 OK",
  "data": {
    "id": "b924d9e1-c8f4-4028-b08f-50559528153f",
    "product": {
      "id": "e6f59424-7a94-4f62-aa8c-c3fa3bc69ec0",
      "name": "Kain",
      "price": 200000,
      "stock": 71
    },
    "quantity": 12,
    "createdAt": "2026-01-16T16:49:48"
  }
}
```