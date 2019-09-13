# teste-brasilprev-java
************************ USUARIO E SENHA OPERACOES REST ********************************
Usuário e senha das operações REST é cadastrado por padrão através do Flyaway.

Usuário: rdmello
Senha: 123456

*****************************************************************************************
*****************************************************************************************
*****************************************************************************************

PRE-REQUISITOS PARA SUBIR A APLICAÇÃO
****************************************************************************
Antes de subir a aplicação leia o arquivo "leia-me.txt" na raiz do projeto

A aplicação foi criada de maneira que deve se conectar a uma instancia do mysql local na porta 3306.

Antes de subir a aplicação é preciso criar um banco chamado brasilprevtest nessa instancia local do mysql, pois o
flyway irá tentar rodar os scripts DDL e DDM

O usuario e senha do banco deverão ser trocados no application.properties

Devido ao tempo curto de desenvolvimento a aplicação não está completa faltando o repositorio de pedidos e seus controllers e serviços porém o padrão para cria-los seria o mesmo utilizado para as demais entidades: Produto, Categoria, Cliente que estão presentes na aplicação

Foi utilizado o spring security, o que exige autenticação para acessar a maioria das operações. 
No arquivo V1_0__add_table.sql dentro da pasta /resource/db/migrations existe o insert de um usuário padrão com a senha em hash.
Usuário: rdmello
Senha: 123456

A cobertura de teste atingiu 56%. Devido ao tempo não foi possivel chegar aos 70, porém a cobertura faltante seguiria o mesmo padrão de testes.
