# Projeto de Programação Concorrente e Distribuída: Sistema de Hotel

Este projeto consiste na implementação de um sistema de hotel utilizando programação concorrente e distribuída. O sistema é composto por diversas entidades, como quartos, hóspedes, camareiras e recepcionistas, cada uma representada por threads que interagem entre si para simular o funcionamento de um hotel real.

## Entidades do Sistema

### Quarto

A classe `Quarto` representa os quartos do hotel. Cada quarto possui um número único, uma indicação de disponibilidade, um objeto `Hospede` que o está ocupando, um estado indicando se está com a chave na recepção e se está sendo limpo.

### Hóspede

A classe `Hospede` representa os hóspedes do hotel. Cada hóspede é representado por uma thread. Eles podem realizar ações como alugar um quarto, sair para passear na cidade, voltar do passeio e terminar sua estadia. Além disso, podem criar reclamações caso não consigam alugar um quarto após algumas tentativas.

### Camareira

A classe `Camareira` representa as camareiras do hotel. Cada camareira é representada por uma thread. Elas são responsáveis por limpar os quartos após a saída dos hóspedes, garantindo que os quartos estejam sempre limpos e prontos para receber novos hóspedes.

### Chave
A classe Chave representa as chaves dos quartos do hotel. Cada chave está associada a um único quarto e é responsável por controlar o acesso ao mesmo.

### Recepcionista

A classe `Recepcionista` representa os recepcionistas do hotel. Cada recepcionista é representado por uma thread. Eles são responsáveis por alocar os hóspedes nos quartos disponíveis, garantindo que cada quarto seja ocupado apenas por hóspedes válidos.

## Regras do Sistema

O sistema segue uma série de regras para garantir o funcionamento adequado do hotel:

- O hotel conta com vários recepcionistas que trabalham juntos e alocam hóspedes apenas em quartos vagos.
- O hotel conta com várias camareiras que limpam os quartos após a saída dos hóspedes.
- Cada quarto possui capacidade para até 4 hóspedes e uma única chave.
- Caso um grupo ou família possua mais do que 4 membros, eles são divididos em vários quartos.
- Os hóspedes devem deixar a chave na recepção ao sair para passear.
- Uma camareira só pode entrar em um quarto se ele estiver vago ou os hóspedes não estiverem nele.
- A limpeza dos quartos é feita sempre após a saída dos hóspedes.
- Um quarto vago que passa por limpeza não pode ser alocado para um novo hóspede.
- Caso uma pessoa chegue e não haja quartos vagos, ela deve esperar em uma fila até que algum quarto fique vago. Se a espera for muito longa, ela pode passear pela cidade e tentar novamente mais tarde.
- Caso a pessoa não consiga alugar um quarto após duas tentativas, ela faz uma reclamação e vai embora.

## Implementação

O sistema foi implementado em Java utilizando threads e locks para garantir a concorrência e evitar condições de corrida. Cada entidade do sistema foi representada por uma classe específica, e as interações entre elas foram modeladas de acordo com as regras do sistema.

O código está organizado em pacotes de acordo com a funcionalidade das classes. Abaixo estão os pacotes e suas respectivas classes:

- **Funcionarios**: Contém as classes `Camareira` e `Recepcionista`, responsáveis por representar os funcionários do hotel.
- **Hotel**: Contém as classes relacionadas ao funcionamento do hotel, como `Hotel`, `Quarto` e `Chave`.
- **Pessoas**: Contém a classe `Pessoa`, que representa os indivíduos do sistema, como hóspedes.
