# Gerenciamento de Biblioteca
Este projeto é um exemplo de gerenciamento de biblioteca utilizando Java,
seguindo os princípios de Programação Orientada a Objetos (POO) e boas práticas de desenvolvimento.
O sistema permite o cadastro de livros, autores e clientes, além de realizar operações básicas de consulta e manipulação de dados.

## Funcionalidades
- Cadastro, edição e consulta de livros, autores, clientes e usuários.
- Controle de empréstimos de livros.
- Interface gráfica com Java Swing.
- Tratamento de exceções e logging para monitoramento de erros.
- Adição de imagens e ícones na interface.
- Sistema de login para acesso às funcionalidades.
- Validação de dados de entrada para garantir a integridade do sistema.
- Persistência de dados em memória (pode ser adaptado para banco de dados).

## Pré-Requisitos
- Java 8 ou superior;
- IDE recomendada: IntelliJ IDEA;
- Opcional: VS Code para edição de código;

## Estrutura do Projeto
- A estrutura do projeto é organizada da seguinte forma:
```
src/
├── Main.java
├── Controller/
│   ├── AutorController.java 
│   ├── ClienteController.java 
│   ├── EmprestimoController.java 
│   ├── LivroController.java 
│   └── UsuarioLoginController.java
├── Logging/
│   └── Logging.java
├── Model/ 
│   ├── Autor.java 
│   ├── Cliente.java 
│   ├── Emprestimo.java 
│   ├── Livro.java 
│   └── UsuarioLogin.java 
├── Utils/ 
│   ├── AdicionarImagemNaTela.java 
│   ├── Icones/ 
│   │   └── Icones.java 
│   └── Image/ 
│       └── IconeJava.png 
└── View/ 
    ├── MenuInicial/ 
    │   ├── LoginUsuarioView.java 
    │   └── PainelPrincipal.java 
    ├── MenuInicialBiblioteca/ 
    │   └── BibliotecaView.java 
    └── MenuModels/ 
        ├── AutorView.java 
        ├── ClienteView.java 
        ├── EmprestimoView.java 
        ├── LivroView.java 
        └── UsuarioView.java
```
## Descrição dos Pacotes

- **Controller**: Controladores responsáveis pela lógica de aplicação e interação entre modelo e visualização.
- **Model**: Classes de modelo que representam as entidades do sistema (Livro, Autor, Cliente, Emprestimo, UsuarioLogin).
- **View**: Telas e painéis da interface gráfica, organizados por contexto (menu inicial, biblioteca, modelos).
- **Utils**: Classes utilitárias, como manipulação de imagens e ícones.
- **Logging**: Implementação de logging para registrar eventos e erros do sistema.

## Como executar
1. Importe o projeto na sua IDE (recomendável IntelliJ).
2. Execute a classe `Main.java` localizada em `src/`.
   - Na classe `Main.java`, é possível habilitar o ambiente de desenvolvimento ou produção, alterando a variável `modoTeste`.
   - Para o ambiente de desenvolvimento, defina `modoTeste` como `true`. Para produção, defina como `false`.
3. Utilize a interface gráfica para interagir com o sistema.
4. Para acessar as funcionalidades, faça login com o usuário padrão:
   - **Usuário**: `admin`
   - **Senha**: `oleques`
---