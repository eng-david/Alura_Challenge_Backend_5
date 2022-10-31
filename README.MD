# ALURA CHALLENGE BACKEND #5
<p align='center'><img src='assets/logo.png' width=240 alt='logo'/></p>

API REST desenvolvida durante o challenge#05 de backend da Alura.

Esta API implementa as seguintes especificações:
1. Rotas no padrão REST com serviço de autenticação.
2. Validações feitas conforme as regras de negócio.
3. Base de dados para persistência das informações.

## Regras de Negócio
1. Todos os campos de vídeos e categorias devem ser obrigatórios e validados.

## Rotas CRUD

Retorna todos os videos:
```
GET /videos
```

Retorna video por id:
```
GET /videos/{id}
```

Deleta video por id:
```
DELETE /videos/{id}
```

Cria um novo vídeo:
```
POST /videos/
```

Atualiza um video por id:
```
PUT /videos/{id}
```

## Tecnologias Utilizadas
- `Linguagem Java`
- `Framework Spring Boot`
- `Maven`
- `Banco de Dados MySQL`

## Pendentes
- [X] Testes para verificar Status code das requisições GET, POST, PUT e DELETE