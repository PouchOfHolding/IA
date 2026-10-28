import java.util.List;


interface Ilayout {

    List<Ilayout> children(); // retorna uma lista com todos os layouts filhos do layout atual
    boolean isGoal(Ilayout l); // verifica se o layout atual é igual ao layout l, ou seja, se o layout atual é o objetivo
    double getK(); // retorna o valor de k do layout atual, que é a soma do número de movimentos feitos para chegar ao layout atual 
    // e o número de movimentos estimados para chegar ao objetivo
}
