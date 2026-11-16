package br.com.starlog.main;

import br.com.starlog.DAO.ProdutoDAO;
import br.com.starlog.DAO.ProdutoDAOSqlite;
import br.com.starlog.model.Produto;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        ProdutoDAO dao = new ProdutoDAOSqlite();
        dao.listarTodos().forEach(p -> dao.excluir(p.getId()));

        System.out.println("=== [CREATE] ===");
        Produto teclado = new Produto("Teclado Mecânico RGB", 349.90, 10);
        Produto mousepad = new Produto("Mousepad Speed XL", 89.90, 25);
        dao.salvar(teclado);
        dao.salvar(mousepad);
        System.out.println("Teclado salvo com ID: " + teclado.getId());
        System.out.println("Mousepad salvo com ID: " + mousepad.getId());

        System.out.println("\n=== [READ] ===");
        imprimirTabela(dao.listarTodos());

        System.out.println("\n=== [UPDATE] ===");
        teclado.setEstoque(15);
        teclado.setPreco(289.90);
        boolean atualizado = dao.atualizar(teclado);
        System.out.println("Atualização retornou: " + atualizado);
        dao.buscarPorId(teclado.getId())
                .ifPresent(p -> System.out.println("Registro atualizado: " + p));

        System.out.println("\n=== [DELETE] ===");
        boolean removido = dao.excluir(mousepad.getId());
        System.out.println("Mousepad (ID " + mousepad.getId() + ") removido? " + removido);

        Long idInexistente = 888L;
        if (dao.excluir(idInexistente)) {
            System.out.println("ID " + idInexistente + " removido.");
        } else {
            System.out.println("Aviso: nenhum produto com ID " + idInexistente
                    + " foi encontrado. Nada foi excluído.");
        }

        System.out.println("\n=== [FINAL STATE] ===");
        List<Produto> restantes = dao.listarTodos();
        imprimirTabela(restantes);
        System.out.println("Total de produtos no banco: " + restantes.size());
    }

    private static void imprimirTabela(List<Produto> produtos) {
        String linha = "+------+---------------------------+------------+---------+";
        System.out.println(linha);
        System.out.printf("| %-4s | %-25s | %10s | %7s |%n", "ID", "NOME", "PREÇO", "ESTOQUE");
        System.out.println(linha);
        for (Produto p : produtos) {
            System.out.printf("| %-4d | %-25s | %10s | %7d |%n",
                    p.getId(), p.getNome(), String.format("R$ %.2f", p.getPreco()), p.getEstoque());
        }
        System.out.println(linha);
    }
}