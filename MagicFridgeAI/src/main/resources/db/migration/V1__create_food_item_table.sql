-- criação da tabela se dá por essa migração e não direto pelo codigo Java
CREATE TABLE food_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    categoria VARCHAR(255) NOT NULL,
    quantidade INT NOT NULL,
    data_vencimento DATE NOT NULL
);