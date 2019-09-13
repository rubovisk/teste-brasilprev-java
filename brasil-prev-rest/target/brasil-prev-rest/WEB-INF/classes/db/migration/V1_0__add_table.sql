create table categorias
(
	idCategoria int not null primary key auto_increment,
    categoria varchar(100)
);

create table clientes
(
	idCliente int not null primary key auto_increment,
    nome varchar(250),
    email varchar(200),
    senha varchar(800),
    rua varchar(200),
    cidade varchar(100),
    bairro varchar(100),
    cep varchar(14),
    estado varchar(2)
);

create table pedidos
(
	idPedido int not null primary key auto_increment,
    `data` date,
    idCliente int,
    `status` varchar(100),
    sessao varchar(250)
);

create table produtos
(
 idProduto int not null primary key auto_increment,
 idCategoria int,
 produto varchar(200),
 preco decimal(13,2),
 quantidade int,
 descricao varchar(200),
 foto longblob
);

create table pedidoItens
(
	idItem int not null primary key auto_increment,
    idPedido int,
    idProduto int,
    produto varchar(200),
    quantidade int,
    valor decimal(13,2),
    subtotal decimal(13,2)
);

alter table pedidos add constraint fk_pedidos_cliente foreign key(idCliente) references clientes(idCliente);
alter table produtos add constraint fk_produto_categoria foreign key(idCategoria) references categorias(idCategoria);
alter table pedidoItens add constraint fk_pedido_itens foreign key(idPedido) references pedidos(idPedido);
alter table pedidoItens add constraint fk_pedidos_itens_produto foreign key(idProduto) references produtos(idProduto);

insert into categorias(categoria) values("seguro");
insert into categorias(categoria) values("empréstimo");
insert into clientes(nome,email,senha,rua,cidade,bairro,cep,estado) values('rdmello','rubovisk@gmail.com','$2a$10$ox62rbtvPf3gHXapIY113./.r4fGYRomaoC9Ct68QOm5weAvYElea','rua estrela','araçatuba','andradina','03041098','MA');