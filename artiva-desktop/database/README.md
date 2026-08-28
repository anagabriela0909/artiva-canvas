# Banco de dados Artiva (MySQL)

Script único: `artiva.sql`

## Como importar no MySQL Workbench

1. Abra o MySQL Workbench e conecte-se ao servidor local.
2. `File > Open SQL Script...` e selecione `artiva.sql`.
3. Clique em executar (raio). O script cria o schema `artiva`
   (utf8mb4 / utf8mb4_unicode_ci), todas as tabelas e os dados iniciais.

> Atenção: o script começa com `DROP DATABASE IF EXISTS artiva;`.

## Tabelas

usuarios, autores, estilos, tecnicas, materiais, obras,
obra_materiais (N:N), favoritos (N:N), conteudos_educativos.

## Relacionamentos

- obras.id_autor → autores.id_autor (UPDATE CASCADE / DELETE RESTRICT)
- obras.id_estilo → estilos.id_estilo (UPDATE CASCADE / DELETE RESTRICT)
- obras.id_tecnica → tecnicas.id_tecnica (UPDATE CASCADE / DELETE RESTRICT)
- obra_materiais.id_obra → obras.id_obra (UPDATE CASCADE / DELETE CASCADE)
- obra_materiais.id_material → materiais.id_material (UPDATE CASCADE / DELETE RESTRICT)
- favoritos.id_usuario → usuarios.id_usuario (UPDATE CASCADE / DELETE CASCADE)
- favoritos.id_obra → obras.id_obra (UPDATE CASCADE / DELETE CASCADE)

## Acesso inicial

- E-mail: admin@artiva.com
- Senha: admin123
- Tipo: ADMINISTRADOR

## Conexão futura pelo Java

O software desktop hoje usa repositórios em memória
(`com.artiva.repository.RepositorioEmMemoria` + `CargaInicial`).
Para ligar ao MySQL basta criar implementações JDBC de
`Repositorio<T>` e trocá-las em `AcervoRepositorios`, sem alterar
serviços, controllers ou views.

String de conexão sugerida:

```
jdbc:mysql://localhost:3306/artiva?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC
```
