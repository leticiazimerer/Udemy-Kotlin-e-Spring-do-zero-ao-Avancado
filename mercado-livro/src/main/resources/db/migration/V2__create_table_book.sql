CREATE TABLE book(
    id int auto_increment primary key,
    name varchar(255) not null,
    price decimal(10,2) not null, -- (10,2) quer dizer que o preço pode ser até 1000000000,99 (10 dígitos antes da vírgula, e 2 após)
    status varchar(255) not null,
    customer_id int not null,
    FOREIGN KEY (customer_id) REFERENCES customer(id) -- serve para mostrar que 'customer_id' é uma chave estrangeira e significa que só podemos adicionar valores no 'customer_id' se eles forem inseridos previamente na coluna 'id' da tabela 'customer'
):