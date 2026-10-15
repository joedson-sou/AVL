import java.util.ArrayList;
import java.util.List;

/**
 * Árvore AVL com passo a passo de cada inserção.
 * Mostra a árvore antes da rotação, o caso (Esq-Esq, Dir-Dir, Esq-Dir, Dir-Esq)
 * e o resultado final.
 */
public class ArvoreAVL {

    static class No {
        int valor;
        int altura = 1;
        No esq, dir;

        No(int valor) {
            this.valor = valor;
        }
    }

    private No raiz;

    // ---------- utilidades ----------

    private int altura(No n) {
        return n == null ? 0 : n.altura;
    }

    /** FB = altura(esquerda) - altura(direita) */
    private int fb(No n) {
        return n == null ? 0 : altura(n.esq) - altura(n.dir);
    }

    private void atualizaAltura(No n) {
        n.altura = 1 + Math.max(altura(n.esq), altura(n.dir));
    }

    // ---------- rotações ----------

    private No rotacaoDireita(No y) {
        No x = y.esq;
        y.esq = x.dir;
        x.dir = y;
        atualizaAltura(y);
        atualizaAltura(x);
        return x;
    }

    private No rotacaoEsquerda(No x) {
        No y = x.dir;
        x.dir = y.esq;
        y.esq = x;
        atualizaAltura(x);
        atualizaAltura(y);
        return y;
    }

    // ---------- inserção ----------

    /** Descrição da rotação feita na última inserção (null se não houve). */
    private String ultimaRotacao;

    public String getUltimaRotacao() {
        return ultimaRotacao;
    }

    public void inserir(int valor) {
        ultimaRotacao = null;
        raiz = inserir(raiz, valor);
    }

    private No inserir(No n, int valor) {
        if (n == null) return new No(valor);

        if (valor < n.valor) n.esq = inserir(n.esq, valor);
        else if (valor > n.valor) n.dir = inserir(n.dir, valor);
        else return n; // sem duplicatas

        atualizaAltura(n);
        int fb = fb(n);

        if (Math.abs(fb) == 2) {
            System.out.println("ANTES da rotação (desbalanceado em " + n.valor + ", FB = " + sinal(fb) + ")");
            imprimir();

            if (fb == 2 && fb(n.esq) >= 0) {
                ultimaRotacao = "Esq-Esq -> rotação simples à direita no nó " + n.valor;
                return rotacaoDireita(n);
            }
            if (fb == -2 && fb(n.dir) <= 0) {
                ultimaRotacao = "Dir-Dir -> rotação simples à esquerda no nó " + n.valor;
                return rotacaoEsquerda(n);
            }
            if (fb == 2) { // filho esquerdo com FB < 0
                ultimaRotacao = "Esq-Dir -> rotação à esquerda no nó " + n.esq.valor
                        + ", depois à direita no nó " + n.valor;
                n.esq = rotacaoEsquerda(n.esq);
                return rotacaoDireita(n);
            }
            // fb == -2 e filho direito com FB > 0
            ultimaRotacao = "Dir-Esq -> rotação à direita no nó " + n.dir.valor
                    + ", depois à esquerda no nó " + n.valor;
            n.dir = rotacaoDireita(n.dir);
            return rotacaoEsquerda(n);
        }
        return n;
    }

    private String sinal(int v) {
        return v > 0 ? "+" + v : String.valueOf(v);
    }

    // ---------- impressão ----------

    /** Desenha a árvore em níveis, centralizada (raiz no topo). */
    public void imprimir() {
        if (raiz == null) {
            System.out.println("(vazia)");
            return;
        }
        int h = alturaReal(raiz); // as alturas guardadas podem estar defasadas antes da rotação
        int largura = (1 << h) * 3;
        char[][] tela = new char[h * 2][largura];
        for (char[] linha : tela) java.util.Arrays.fill(linha, ' ');
        desenhar(tela, raiz, 0, 0, largura - 1);
        for (char[] linha : tela) {
            String s = new String(linha).replaceAll("\\s+$", "");
            if (!s.isEmpty()) System.out.println(s);
        }
    }

    private int alturaReal(No n) {
        return n == null ? 0 : 1 + Math.max(alturaReal(n.esq), alturaReal(n.dir));
    }

    private void desenhar(char[][] tela, No n, int nivel, int ini, int fim) {
        if (n == null) return;
        int meio = (ini + fim) / 2;
        String txt = String.valueOf(n.valor);
        for (int i = 0; i < txt.length(); i++) tela[nivel * 2][meio + i] = txt.charAt(i);

        if (n.esq != null) {
            int m = (ini + meio) / 2;
            tela[nivel * 2 + 1][(m + meio) / 2] = '/';
            desenhar(tela, n.esq, nivel + 1, ini, meio);
        }
        if (n.dir != null) {
            int m = (meio + fim) / 2;
            tela[nivel * 2 + 1][(m + meio) / 2 + 1] = '\\';
            desenhar(tela, n.dir, nivel + 1, meio, fim);
        }
    }

    public String emOrdem() {
        List<Integer> l = new ArrayList<>();
        emOrdem(raiz, l);
        return l.toString();
    }

    private void emOrdem(No n, List<Integer> l) {
        if (n == null) return;
        emOrdem(n.esq, l);
        l.add(n.valor);
        emOrdem(n.dir, l);
    }

    public String preOrdem() {
        List<Integer> l = new ArrayList<>();
        preOrdem(raiz, l);
        return l.toString();
    }

    private void preOrdem(No n, List<Integer> l) {
        if (n == null) return;
        l.add(n.valor);
        preOrdem(n.esq, l);
        preOrdem(n.dir, l);
    }
}
