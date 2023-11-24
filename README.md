Projeto para estudar Spring Security
---

### Executar o Dockerfile

#### Criar o build:
`docker buildx build --tag jwt-auth-crud-demo:v1 .`

#### Subir a aplicação 
`docker run -d --name app_crud_jwt -p 8080:8080 jwt-auth-crud-demo:v1`

---

