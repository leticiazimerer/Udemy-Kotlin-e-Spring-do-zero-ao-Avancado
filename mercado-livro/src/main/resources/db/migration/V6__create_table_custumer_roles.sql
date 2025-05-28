CREATE TABLE customer_roles(
    customer_id int not null,
    role varchar(50) not null,
    FOREIGN KEY (customer_id) REFERENCES customer(id) -- serve para mostrar que 'customer_id' é uma chave estrangeira e significa que só podemos adicionar valores no 'customer_id' se eles forem inseridos previamente na coluna 'id' da tabela 'customer'
):