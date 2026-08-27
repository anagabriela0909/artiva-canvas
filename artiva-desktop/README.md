# Artiva — Desktop JavaFX

Aplicativo desktop de galeria e aprendizado sobre pintura e arte, desenvolvido em **Java 21+** com **JavaFX 21**.

## Pré-requisitos

- Java JDK 21 ou superior
- Maven 3.9+
- JavaFX SDK 21 (resolvido automaticamente pelo Maven)

## Executar

A partir desta pasta (`artiva-desktop/`):

```bash
mvn javafx:run
```

A classe principal é `com.artiva.ArtivaApplication`. Ao executar, a janela do Artiva abre diretamente na tela de login.

## Compilar e empacotar

```bash
mvn clean package
```

O `.jar` gerado estará em `target/`. Para executar o jar distribuível, certifique-se de incluir os módulos do JavaFX.

## Acesso de demonstração

- Usuário comum: `ana` / `artiva`
- Curadoria (admin): `admin` / `admin`

## Estrutura do projeto

```
src/main/java/com/artiva/
  ArtivaApplication.java   # ponto de entrada JavaFX
  controller/              # navegação e controladores de tela
  model/                   # entidades e enum (POO)
  repository/              # repositórios em memória
  service/                 # regras de negócio
  view/                    # telas JavaFX e componentes

src/main/resources/com/artiva/
  css/artiva.css            # design system
  images/                   # imagens das obras
```

## Sobre o software

O Artiva foi pensado como trabalho acadêmico de Programação Orientada a Objetos, separando modelos, serviços, repositórios e views. A interface evita aparência genérica de sistema administrativo, buscando a estética de uma galeria digital contemporânea.
