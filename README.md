# Artiva Canvas

## CONCEITO DO SOFTWARE

Artiva é um software de exploração e aprendizado sobre pintura e arte.

O objetivo é tornar a pintura mais acessível e desmistificar a ideia de que arte é algo distante ou exclusivo de especialistas.

O usuário poderá:

- explorar obras de arte;

- conhecer artistas;

- aprender técnicas de pintura;

- descobrir estilos artísticos;

- conhecer materiais;

- salvar obras favoritas;

- pesquisar conteúdos;

- encontrar obras por nível de dificuldade.

O administrador poderá:

- cadastrar obras;

- editar obras;

- remover obras;

- cadastrar artistas;

- cadastrar estilos;

- cadastrar técnicas;

- gerenciar conteúdos da galeria.

---

# IDENTIDADE VISUAL

Manter esta identidade:

Paleta:

#F8F7F3 — branco quente / fundo principal

#E8E6E1 — cinza claro

#B8B5AE — cinza médio

#292825 — grafite

#D99A2B — amarelo mostarda

#C87532 — ocre alaranjado

#80502F — marrom queimado

#F1E3C5 — creme

Distribuição:

- 70% tons claros e neutros

- 15% cinzas

- 10% mostarda

- 5% tons ocres

A interface deve transmitir:

- galeria de arte contemporânea;

- museu moderno;

- revista editorial;

- livros de arte;

- papel e tela de pintura;

- criatividade;

- acessibilidade.

Evitar:

- aparência de sistema administrativo;

- interface genérica Java;

- excesso de cores;

- neon;

- estilo infantil;

- aparência de formulário.

---

# ESTRUTURA DO SOFTWARE

Criar as seguintes telas JavaFX:

## 1. Tela de Login

Componentes:

- logo Artiva;

- campo usuário;

- campo senha;

- botão entrar;

- opção criar conta.

## 2. Tela Inicial / Feed

Esta é a principal tela do software.

Não deve parecer um dashboard.

Deve parecer uma galeria digital interativa.

Criar:

Header:

- logo Artiva;

- menu:

  - Explorar

  - Obras

  - Estilos

  - Técnicas

  - Artistas

  - Favoritos

Campo de pesquisa.

Avatar do usuário.

---

# FEED PRINCIPAL

Criar as seguintes seções:

## 🖼️ Obras em destaque

Mostrar cards grandes contendo:

- imagem da obra;

- título;

- autor;

- estilo;

- técnica;

- dificuldade.

Exemplo:

Noite Estrelada

Vincent van Gogh

Pós-Impressionismo

Óleo sobre tela

Avançado

---

## 🆕 Obras recém-cadastradas

Mostrar:

- imagem;

- título;

- artista;

- estilo;

- indicador NOVO.

---

## 🎨 Descubra um estilo

Criar cards para:

- Impressionismo;

- Pós-Impressionismo;

- Barroco;

- Realismo;

- Expressionismo;

- Surrealismo.

Cada estilo possui:

- imagem;

- descrição;

- nível de dificuldade.

---

## 🖌️ Técnica da semana

Criar um bloco educativo.

Exemplo:

Impasto

Informações:

- descrição;

- dificuldade;

- materiais necessários;

- obras relacionadas.

---

## 👨‍🎨 Artista em destaque

Layout dividido:

Imagem do artista.

Informações:

- nome;

- período;

- estilo;

- biografia;

- curiosidade.

---

## 📚 Para você aprender

Cards educativos:

- Como funciona a pintura a óleo?

- O que é perspectiva?

- Como escolher um pincel?

- O que são cores complementares?

- Como funciona a aquarela?

---

## ⭐ Obras populares

Mostrar:

- obras favoritas;

- quantidade de favoritos;

- botão favoritar.

---

## 🌱 Recomendado para iniciantes

Mostrar:

- obras fáceis;

- estilos acessíveis;

- técnicas básicas.

Mensagem:

“Não precisa ser especialista para começar.”

---

# INTERAÇÃO

Implementar:

Ao passar o mouse:

- zoom suave na imagem;

- destaque visual;

- animação discreta.

Ao clicar:

Abrir tela de detalhes da obra.

---

# TELA DE DETALHAMENTO DA OBRA

Criar uma tela contendo:

- imagem;

- título;

- autor;

- ano;

- estilo;

- técnica;

- dificuldade;

- descrição;

- contexto histórico;

- materiais utilizados;

- curiosidades.

---

# MODELAGEM ORIENTADA A OBJETOS

O sistema deve ser realmente desenvolvido utilizando POO.

Criar classes organizadas:

Usuario

Administrador extends Usuario

Obra

Autor

Estilo

Tecnica

Material

Favorito

Aplicar:

- encapsulamento;

- abstração;

- herança;

- polimorfismo quando aplicável;

- associação entre objetos.

---

# ORGANIZAÇÃO DO PROJETO JAVA

Separar:

src

├── model

├── controller

├── view

├── service

├── repository

├── resources

│   ├── images

│   └── css

A interface JavaFX não deve conter toda a lógica.

Separar regras de negócio das telas.

---

# DADOS INICIAIS

Utilizar dados fictícios realistas.

Adicionar obras famosas:

- A Noite Estrelada — Vincent van Gogh

- Impressão, Nascer do Sol — Claude Monet

- Nenúfares — Claude Monet

- A Grande Onda de Kanagawa — Katsushika Hokusai

- O Grito — Edvard Munch

As imagens devem ficar armazenadas localmente nos recursos do projeto.

---

# BANCO DE DADOS

Preparar o projeto para futura integração com banco de dados.

Criar estrutura que permita substituir os dados mockados posteriormente.

---

# EXECUÇÃO

O projeto deve possuir uma classe principal:

ArtivaApplication.java

Ao executar:

1. iniciar JavaFX;

2. abrir janela do software;

3. carregar interface;

4. permitir navegação.

Não abrir navegador.

Não depender de servidor.

Não depender da Lovable.

Não utilizar WebView.

---

# RESULTADO ESPERADO

Entregar um software Java desktop chamado ARTIVA.

Ele deve parecer uma galeria digital de arte moderna.

Deve possuir:

- interface refinada;

- feed completo;

- navegação;

- usuários;

- favoritos;

- obras;

- artistas;

- estilos;

- técnicas;

- materiais;

- área administrativa;

- estrutura POO.

A prioridade é:

1. Qualidade visual.

2. Fidelidade à identidade do Artiva.

3. Organização do código Java.

4. Aplicação correta de Programação Orientada a Objetos.

5. Funcionamento como software desktop executável.

This project was built with [Lovable](https://lovable.dev).

## Build with Lovable

Continue developing this project in the [Lovable editor](https://lovable.dev/projects/50544f8a-a264-4871-9c96-e09dd4037b26).

- **Ship faster**: describe what you want to build and Lovable handles the code.
- **Stay in sync**: every change made in Lovable is committed straight to this repository.
- **Full ownership**: this code is yours. Push to `main` on GitHub and your changes sync back into Lovable, ready for your next prompt.

## Development

Prefer working locally? You need Node.js and npm — [install with nvm](https://github.com/nvm-sh/nvm#installing-and-updating).

```sh
git clone <this-repository-url>
cd <repository-name>
npm i
npm run dev
```
