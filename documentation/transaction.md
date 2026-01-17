## PRODUCT API Documentation

### 1. Create Product❌ ✅

**`POST /e-commerce/products`**

📥 Request Body:

```json
{
  "name": "parfum",
  "price": 200000,
  "stock": 5
}
```

📄 Response (201 Created):

```json
{
  "status": "201 CREATED",
  "data": {
    "id": "ea4d9742-d072-410f-a60e-34b6cbab4c71",
    "name": "parfum",
    "price": 200000,
    "stock": 5
  }
}

```

### 2. Gel All Product❌ ✅

**`GET /e-commerce/products`**

📄 Response (200 OK):

```json
{
  "status": "200 CREATED",
  "data": [
    {
      "id": "ea4d9742-d072-410f-a60e-34b6cbab4c71",
      "name": "parfum",
      "price": 200000,
      "stock": 5
    },
    {
      "id": "ea4d9742-d072-410f-a60e-34basdas6cbab4c71",
      "name": "susu",
      "price": 230000,
      "stock": 2
    },
    {
      "id": "ea4d9742-d072-4asdas10f-a60e-34basdas6cbab4c71",
      "name": "ayam",
      "price": 20000,
      "stock": 3
    }
  ]
}
```

### 3. Get Product❌ ✅

**`GET /e-commerce/products/{product_id}`**

📄 Response (200 OK):

```json
{
  "status": "200 CREATED",
  "data": {
    "id": "ea4d9742-d072-410f-a60e-34b6cbab4c71",
    "name": "parfum",
    "price": 200000,
    "stock": 5
  }
}
```

### 4. Update Product❌ ✅

**`PATCH /e-commerce/products/{product_id}`**

📥 Request Body:

```json
 {
  "name": "susu",
  "price": 230000,
  "stock": 2
}
```

📄 Response (201 Created):

```json
{
  "status": "201 OK",
  "data": {
    "id": "ea4d9742-d072-410f-a60e-34basdas6cbab4c71",
    "name": "susu",
    "price": 230000,
    "stock": 2
  }
}

```