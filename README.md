Projeto para estudar Spring Security
---

### Executar o Dockerfile

#### Criar o build:
`docker buildx build --tag jwt-auth-crud-demo:v1 .`

#### Subir a aplicação 
`docker run -d --name app_crud_jwt -p 8080:8080 jwt-auth-crud-demo:v1`

---

#### Subindo uma instância do Banco PostgreSQL no Docker
`docker run -d -e POSTGRES_HOST_AUTH_METHOD=trust -e POSTGRES_USER=user_backend -e POSTGRES_PASSWORD=password_backend -e POSTGRES_DB=authcruddb --name db-postgresql -p 5432:5432 postgres:16.1-alpine`