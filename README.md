# <img src="https://img.shields.io/badge/iFood-EA1D2C?style=for-the-badge&logo=ifood&logoColor=white"> IFOOD Clone 

>Projeto desenvolvido para a disciplina de Programação 2 do curso de Engenharia de Computação do CESUPA.>

## 💻Sobre o Projeto

Este projeto consiste em um sistema desktop de pedidos de restaurante, simulando a experiência de aplicativos de delivery como o iFood. O objetivo principal é aplicar conceitos avançados de **Programação Orientada a Objetos (POO)**, incluindo herança, polimorfismo, encapsulamento e interfaces, além de integrar persistência de dados e funcionalidades modernas de autenticação e pagamento.

<img width="150" height="150" alt="image" src= https://github.com/user-attachments/assets/bd15d5a4-4f3c-4d6a-9593-20546b402486
/>

O sistema atende a dois tipos de usuários: **Dono do Restaurante** e **Cliente**.

---

## 🍽Funcionalidades

### Autenticação e Segurança (Extra)
* **Login Seguro:** Sistema de login para Clientes e Donos.
* ![Segurança](https://img.shields.io/badge/Security-Email-red?style=flat-square&logo=gmail): Envio de código de verificação para o e-mail do usuário para validar o acesso.

### Para o Dono do Restaurante
* **Cadastro de Restaurante:** Inserção de nome e endereço do estabelecimento.
* **Gerenciamento de Produtos:**
    * Cadastro de produtos com nome, descrição, preço e tempo de preparo.
    * Distinção entre **Comida** (tipo de cozinha, vegetariano/vegano) e **Bebida** (tamanho em ml, alcoólica/não alcoólica).
    * Edição e remoção de itens do cardápio.
    * <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white">: Todas as alterações são salvas automaticamente no banco de dados.

### Para o Cliente
* **Catálogo de Restaurantes:** Visualização da lista de restaurantes cadastrados.
* **Realização de Pedidos:**
    * Seleção de restaurante e adição de produtos ao carrinho.
    * Cálculo automático do **preço total** e **tempo estimado de espera**.
* ![Pagamento](https://img.shields.io/badge/Pagamento-QR_Code-blue?style=flat-square&logo=qrcode): Geração dinâmica de um QR Code para pagamento ao finalizar o pedido.

---

## ☕ Tecnologias Utilizadas

* **Linguagem:** Java
* **Interface Gráfica:** JavaFX - Scene Biulder
* **Banco de Dados:** MySQL
* **APIs e Bibliotecas:**
    
---

## 🗂Arquitetura e Conceitos de POO

Este projeto foi avaliado com base em cinco competências essenciais:

1.  **Classes e Objetos:** Uso de encapsulamento com atributos privados e métodos acessores (Getters/Setters), além de construtores flexíveis.
2.  **Herança:**
    * `Usuario` é a superclasse de `Cliente` e `DonoRestaurante`.
    * `Produto` é a superclasse de `Comida` e `Bebida`.
3.  **Polimorfismo:** Tratamento genérico de listas de produtos e usuários, utilizando sobrescrita de métodos para comportamentos específicos.
4.  **Interfaces:** Feitos pelo Scene Builder
5.  **Organização:** Código limpo, comentado e estruturado em pacotes.

---

## Modelo de Dados (UML)

O sistema segue o seguinte diagrama de classes planejado:

---

## ⚙️ Como Executar o Projeto

### Pré-requisitos
* Java JDK instalado.
* Servidor de Banco de Dados MySQLrodando.
* IDE VScode
