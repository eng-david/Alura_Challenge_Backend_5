# ALURA CHALLENGE BACKEND #5
<p align='center'><img src='assets/logo.png' width=240 alt='logo'/></p>

API REST desenvolvida durante o challenge#05 de backend da Alura.

Esta API implementa as seguintes especificações:
1. Rotas no padrão REST com serviço de autenticação.
2. Validações feitas conforme as regras de negócio.
3. Base de dados para persistência das informações.

## Regras de Negócio
1. Todos os campos de vídeos e categorias devem ser obrigatórios e validados.
2. A categoria com ID = 1, deve chamar LIVRE e caso ela não seja especificada na criação do vídeo, será atribuído o ID = 1.

## Rotas CRUD

<table>
    <tr>
        <th>Method</th>
        <th>Route</th>
        <th>Description</th>
        <th>Body Param.</th>
        <th>Query Param.</th>
    </tr>
    <tr>
        <td>GET</td>
        <td>/videos</td>
        <td>Retorna todos os vídeos</td>
        <td>-</td>
        <td>-</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/videos/{id}</td>
        <td>Retorna video</td>
        <td>-</td>
        <td>video id</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/videos/?search=</td>
        <td>Buscar um vídeo pelo nome</td>
        <td>-</td>
        <td>nome a ser buscado</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/videos/{id}</td>
        <td>Deleta video</td>
        <td>-</td>
        <td>video id</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/videos</td>
        <td>Cria um novo vídeo</td>
        <td>{"titulo","descricao","url","categoriaId"}</td>
        <td>-</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/videos/{id}</td>
        <td>Atualizar video</td>
        <td>{"titulo","descricao","url","categoriaId"}</td>
        <td>video id</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/categorias</td>
        <td>Retorna todas as categorias</td>
        <td>-</td>
        <td>-</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/categorias/{id}</td>
        <td>Retorna categoria</td>
        <td>-</td>
        <td>categoria id</td>
    </tr>
    <tr>
        <td>GET</td>
        <td>/categorias/{id}/videos</td>
        <td>Retorna todos os videos pertencentes a uma categoria</td>
        <td>-</td>
        <td>categoria id</td>
    </tr>
    <tr>
        <td>DELETE</td>
        <td>/categorias/{id}</td>
        <td>Deleta categoria</td>
        <td>-</td>
        <td>categoria id</td>
    </tr>
    <tr>
        <td>POST</td>
        <td>/categorias</td>
        <td>Cria uma nova categoria</td>
        <td>{"titulo","cor"}</td>
        <td>-</td>
    </tr>
    <tr>
        <td>PUT</td>
        <td>/categorias/{id}</td>
        <td>Atualizar categoria</td>
        <td>{"titulo","cor"}</td>
        <td>categoria id</td>
    </tr>
</table>


## Tecnologias Utilizadas
- `Linguagem Java`
- `Framework Spring Boot`
- `Maven`
- `Banco de Dados MySQL`

## Pendentes Semana 1
- [X] Testes para verificar Status code das requisições GET, POST, PUT e DELETE

## Pendentes Semana 2
- [X] Adicionar categorias e seus campos na base de dados;
- [X] Rotas CRUD para /categorias;
- [X] Incluir campo categoriaId no modelo video;
- [X] Escrever os testes necessários.