# Quick Start

1. **Start the app:**
   ```bash
   docker-compose up --build
   ```

## Database Details

- **Host:** localhost
- **Port:** 3306
- **Database:** kulinaria
- **Username:** appuser
- **Password:** tajnehaslo

## Schema

The following tables are created:

### users
- `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- `username` (VARCHAR(255), UNIQUE, NOT NULL)
- `passwordHash` (VARCHAR(255), NOT NULL)

### recipes
- `id` (BIGINT, PRIMARY KEY, AUTO_INCREMENT)
- `title` (VARCHAR(255))
- `description` (TEXT)
- `author_id` (BIGINT, FOREIGN KEY to users.id)

### recipe_likes
- `recipe_id` (BIGINT, FOREIGN KEY to recipes.id)
- `user_id` (BIGINT, FOREIGN KEY to users.id)
- PRIMARY KEY (recipe_id, user_id)
