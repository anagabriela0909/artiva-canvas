package com.artiva.repository;

import com.artiva.model.Administrador;
import com.artiva.model.ConteudoEducativo;
import com.artiva.model.Dificuldade;
import com.artiva.model.Estilo;
import com.artiva.model.Autor;
import com.artiva.model.Material;
import com.artiva.model.Obra;
import com.artiva.model.Tecnica;
import com.artiva.model.Visitante;

/**
 * Popula o acervo com dados ficticios realistas.
 * Ao integrar um banco de dados esta classe deixa de ser usada.
 */
public final class CargaInicial {

    private CargaInicial() {
    }

    public static void popular(AcervoRepositorios repos) {
        // ----- materiais -----
        Material oleo = new Material("Tinta a oleo", "Pigmento em base oleosa, secagem lenta e cores profundas.");
        Material tela = new Material("Tela de linho", "Suporte tradicional, textura firme para camadas espessas.");
        Material espatula = new Material("Espatula", "Aplica massa de tinta criando relevo.");
        Material pincelChato = new Material("Pincel chato", "Ideal para blocos de cor e pinceladas marcadas.");
        Material aquarela = new Material("Aquarela em pastilha", "Pigmento diluido em agua, camadas transparentes.");
        Material papelAlgodao = new Material("Papel 100% algodao", "Absorve agua sem deformar.");
        Material bloco = new Material("Bloco de madeira", "Matriz entalhada para impressao.");
        Material tintaSumi = new Material("Tinta sumi", "Tinta a base de fuligem usada na xilogravura.");
        for (Material m : new Material[]{oleo, tela, espatula, pincelChato, aquarela, papelAlgodao, bloco, tintaSumi}) {
            repos.materiais().salvar(m);
        }

        // ----- tecnicas -----
        Tecnica impasto = new Tecnica("Impasto",
                "Aplicacao de tinta em camadas espessas, deixando a marca do pincel ou da espatula visivel. "
                        + "A luz bate no relevo e a superficie ganha movimento.",
                Dificuldade.AVANCADO, oleo, tela, espatula, pincelChato);
        Tecnica oleoSobreTela = new Tecnica("Oleo sobre tela",
                "Tecnica classica de camadas sobrepostas com tinta a oleo.",
                Dificuldade.INTERMEDIARIO, oleo, tela, pincelChato);
        Tecnica aquarelaTecnica = new Tecnica("Aquarela",
                "Camadas transparentes de pigmento diluido em agua, do mais claro ao mais escuro.",
                Dificuldade.INICIANTE, aquarela, papelAlgodao);
        Tecnica xilogravura = new Tecnica("Xilogravura",
                "Entalhe em madeira e impressao das areas em relevo.",
                Dificuldade.INTERMEDIARIO, bloco, tintaSumi, papelAlgodao);
        Tecnica temperaTecnica = new Tecnica("Tempera sobre cartao",
                "Pigmento ligado por emulsao, secagem rapida e acabamento fosco.",
                Dificuldade.INTERMEDIARIO, pincelChato, papelAlgodao);
        for (Tecnica t : new Tecnica[]{impasto, oleoSobreTela, aquarelaTecnica, xilogravura, temperaTecnica}) {
            repos.tecnicas().salvar(t);
        }

        // ----- estilos -----
        Estilo impressionismo = new Estilo("Impressionismo", "1870 - 1890",
                "Pinceladas soltas e luz em primeiro plano. Pintar a sensacao do instante, nao o detalhe.",
                Dificuldade.INICIANTE, "impressao_nascer_do_sol.jpg");
        Estilo posImpressionismo = new Estilo("Pos-Impressionismo", "1886 - 1905",
                "Cor expressiva e forma pessoal: a emocao guia o gesto do pintor.",
                Dificuldade.AVANCADO, "noite_estrelada.jpg");
        Estilo barroco = new Estilo("Barroco", "1600 - 1750",
                "Contraste dramatico entre luz e sombra, movimento e teatralidade.",
                Dificuldade.AVANCADO, "artista_destaque.jpg");
        Estilo realismo = new Estilo("Realismo", "1840 - 1880",
                "A vida comum observada sem idealizacao, com atencao ao cotidiano.",
                Dificuldade.INTERMEDIARIO, "nenufares.jpg");
        Estilo expressionismo = new Estilo("Expressionismo", "1905 - 1930",
                "Distorcao proposital da forma para revelar estados interiores.",
                Dificuldade.INTERMEDIARIO, "o_grito.jpg");
        Estilo surrealismo = new Estilo("Surrealismo", "1924 - 1950",
                "Imagens do sonho e do inconsciente em combinacoes inesperadas.",
                Dificuldade.INTERMEDIARIO, "grande_onda.jpg");
        Estilo ukiyoE = new Estilo("Ukiyo-e", "1600 - 1900",
                "Gravura japonesa de linhas firmes e planos de cor bem definidos.",
                Dificuldade.INTERMEDIARIO, "grande_onda.jpg");
        for (Estilo e : new Estilo[]{impressionismo, posImpressionismo, barroco, realismo,
                expressionismo, surrealismo, ukiyoE}) {
            repos.estilos().salvar(e);
        }

        // ----- autores -----
        Autor vanGogh = new Autor("Vincent van Gogh", "1853 - 1890", "Holandes",
                "Pintou mais de 2.000 obras em pouco mais de dez anos. Sua pincelada espessa e vibrante "
                        + "transformou a paisagem em emocao pura e abriu caminho para a arte moderna.",
                "Vendeu pouquissimos quadros durante a vida e escrevia cartas detalhadas ao irmao Theo "
                        + "explicando cada escolha de cor.",
                "artista_destaque.jpg");
        Autor monet = new Autor("Claude Monet", "1840 - 1926", "Frances",
                "Fundador do Impressionismo. Pintava a mesma cena em horarios diferentes para estudar a luz.",
                "O jardim de Giverny foi construido por ele para servir de modelo.",
                "artista_destaque.jpg");
        Autor hokusai = new Autor("Katsushika Hokusai", "1760 - 1849", "Japones",
                "Mestre da gravura ukiyo-e, autor da serie Trinta e Seis Vistas do Monte Fuji.",
                "Trocou de nome artistico mais de trinta vezes ao longo da carreira.",
                "grande_onda.jpg");
        Autor munch = new Autor("Edvard Munch", "1863 - 1944", "Noruegues",
                "Precursor do Expressionismo, investigou angustia, amor e morte.",
                "Fez quatro versoes de O Grito em tecnicas diferentes.",
                "o_grito.jpg");
        for (Autor a : new Autor[]{vanGogh, monet, hokusai, munch}) {
            repos.autores().salvar(a);
        }

        // ----- obras -----
        Obra noiteEstrelada = new Obra("A Noite Estrelada", vanGogh, 1889, posImpressionismo, impasto,
                Dificuldade.AVANCADO, "noite_estrelada.jpg");
        noiteEstrelada.setDescricao("Um ceu em espiral sobre um vilarejo adormecido, com o cipreste ligando "
                + "terra e ceu em uma unica pincelada continua.");
        noiteEstrelada.setContextoHistorico("Pintada durante a internacao no sanatorio de Saint-Remy-de-Provence, "
                + "a partir da vista da janela combinada com memoria e imaginacao.");
        noiteEstrelada.setCuriosidade("Van Gogh considerava a obra um fracasso: dizia que havia exagerado nas estrelas.");
        noiteEstrelada.setTotalFavoritos(482);

        Obra nascerDoSol = new Obra("Impressao, Nascer do Sol", monet, 1872, impressionismo, oleoSobreTela,
                Dificuldade.INTERMEDIARIO, "impressao_nascer_do_sol.jpg");
        nascerDoSol.setDescricao("Porto de Le Havre envolvido em neblina, com o sol laranja refletido na agua.");
        nascerDoSol.setContextoHistorico("O titulo da obra deu nome ao Impressionismo, usado inicialmente como critica.");
        nascerDoSol.setCuriosidade("Monet pintou a cena em poucas horas para capturar a luz exata do amanhecer.");
        nascerDoSol.setTotalFavoritos(311);

        Obra nenufares = new Obra("Nenufares", monet, 1916, impressionismo, oleoSobreTela,
                Dificuldade.INICIANTE, "nenufares.jpg");
        nenufares.setDescricao("Superficie de agua coberta por vitorias-regias, sem horizonte nem margem visivel.");
        nenufares.setContextoHistorico("Parte da serie pintada no jardim de Giverny nos ultimos anos de vida do artista.");
        nenufares.setCuriosidade("Monet pintou cerca de 250 telas com o mesmo lago.");
        nenufares.setNovidade(true);
        nenufares.setTotalFavoritos(267);

        Obra grandeOnda = new Obra("A Grande Onda de Kanagawa", hokusai, 1831, ukiyoE, xilogravura,
                Dificuldade.INTERMEDIARIO, "grande_onda.jpg");
        grandeOnda.setDescricao("Uma onda gigante com garras de espuma prestes a cobrir tres barcos, "
                + "com o Monte Fuji pequeno ao fundo.");
        grandeOnda.setContextoHistorico("Gravura da serie Trinta e Seis Vistas do Monte Fuji, produzida em larga escala "
                + "e vendida por precos populares no Japao do periodo Edo.");
        grandeOnda.setCuriosidade("O azul intenso vem do pigmento Azul da Prussia, recem-importado da Europa.");
        grandeOnda.setNovidade(true);
        grandeOnda.setTotalFavoritos(398);

        Obra oGrito = new Obra("O Grito", munch, 1893, expressionismo, temperaTecnica,
                Dificuldade.INTERMEDIARIO, "o_grito.jpg");
        oGrito.setDescricao("Figura de rosto deformado em uma ponte, sob um ceu ondulado em laranja e ocre.");
        oGrito.setContextoHistorico("Munch relatou em seu diario ter sentido um grito atravessar a natureza "
                + "durante um passeio ao anoitecer em Oslo.");
        oGrito.setCuriosidade("Existe uma inscricao a lapis na tela, provavelmente escrita pelo proprio artista.");
        oGrito.setNovidade(true);
        oGrito.setTotalFavoritos(455);

        for (Obra o : new Obra[]{noiteEstrelada, nascerDoSol, nenufares, grandeOnda, oGrito}) {
            repos.obras().salvar(o);
        }

        // ----- conteudos educativos -----
        repos.conteudos().salvar(new ConteudoEducativo("Como funciona a pintura a oleo?",
                "Camadas, tempo de secagem e a regra do gordo sobre magro explicados sem jargao.",
                "6 min", Dificuldade.INICIANTE));
        repos.conteudos().salvar(new ConteudoEducativo("O que e perspectiva?",
                "Como criar profundidade com linhas de fuga e um unico ponto de observacao.",
                "5 min", Dificuldade.INICIANTE));
        repos.conteudos().salvar(new ConteudoEducativo("Como escolher um pincel?",
                "Formatos, cerdas e para que serve cada um na pratica.",
                "4 min", Dificuldade.INICIANTE));
        repos.conteudos().salvar(new ConteudoEducativo("O que sao cores complementares?",
                "Pares opostos no circulo cromatico e por que criam contraste vibrante.",
                "3 min", Dificuldade.INICIANTE));
        repos.conteudos().salvar(new ConteudoEducativo("Como funciona a aquarela?",
                "Agua, transparencia e o valor do branco do papel.",
                "5 min", Dificuldade.INTERMEDIARIO));

        // ----- usuarios -----
        repos.usuarios().salvar(new Visitante("Ana Carvalho", "ana", "artiva"));
        repos.usuarios().salvar(new Administrador("Curadoria Artiva", "admin", "admin", "Acervo"));
    }
}
