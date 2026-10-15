import java.util.Arrays;

/** Classe de execução: roda as 4 árvores da atividade AVL. */
public class Main {

    static void executar(int numero, int[] valores) {
        System.out.println("==================================================");
        System.out.println("Árvore " + numero + " – " + Arrays.toString(valores));
        System.out.println("==================================================");

        ArvoreAVL avl = new ArvoreAVL();
        for (int v : valores) {
            System.out.println();
            avl.inserir(v);
            if (avl.getUltimaRotacao() == null) {
                System.out.println("Inserir " + v + " – sem rotação");
            } else {
                System.out.println("Inserir " + v + " – Caso " + avl.getUltimaRotacao());
            }
            avl.imprimir();
        }
        System.out.println();
        System.out.println(">> Resultado final");
        avl.imprimir();
        System.out.println("Em ordem : " + avl.emOrdem());
        System.out.println("Pré-ordem: " + avl.preOrdem());
        System.out.println();
    }

    static void main(String[] args) {
        executar(1, new int[]{13, 10, 15, 5, 11, 16, 4, 6, 14, 3});
        executar(2, new int[]{30, 5, 35, 32, 40, 45});
        executar(3, new int[]{13, 10, 15, 5, 11, 16, 4, 6, 7});
        executar(4, new int[]{5, 2, 7, 1, 4, 6, 9, 3, 8, 15});
    }
}