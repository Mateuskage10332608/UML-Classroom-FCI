<h2><a href= "https://www.mackenzie.br">Universidade Presbiteriana Mackenzie</a></h2>

# Projeto: Sistema de Monitoramento de Plantações com Drones

# Grupo: Yan Andreotti dos Santos, Mateus Kage Moya

# Descrição

Este projeto tem como objetivo modelar e implementar um sistema de software seguro para uma cooperativa rural monitorar plantações usando drones. O sistema permitirá o cadastro de áreas agrícolas, drones, o agendamento de missões de voo para coleta de dados (como temperatura, umidade e imagens ) e a geração de relatórios básicos. A segurança é um requisito fundamental, sendo aplicada desde a fase de modelagem até a implementação final.

# Contexto

Durante esta disciplina, você e sua equipe irão desenvolver, de forma incremental, um projeto de software completo, desde a concepção até a implementação em Java, utilizando UML para modelagem e GitHub para controle de versão.

Em suma: o projeto será desenvolvido de forma incremental, avaliando sua evolução a cada entrega, até a implementação final em Java. O foco é unir boa modelagem, implementação correta e segurança desde o início.

# O que deve ser feito ?

- Modelar e implementar um sistema de software em equipes de até 02 alunos.

- Aplicar conceitos de análise e projeto orientado a objetos, utilizando UML (classes, sequência, colaboração e estados).

- Implementar em Java, com versionamento e organização via GitHub.

- Garantir que a segurança esteja presente desde a modelagem até a implementação (security by design).

# Como deve ser feito

- Modelagem inicial: elaborar diagramas de classes e sequência, respeitando boas práticas de encapsulamento e validação de dados.

- Integração dos modelos: consolidar classes, interações, estados e colaboração, prevendo restrições de segurança.

- Implementação: transformar os modelos em código Java, aplicando programação segura (validação de entradas, tratamento de exceções e uso de prepared statements).

- Controle de versão: utilizar GitHub desde a primeira entrega, com commits organizados, e sem credenciais ou dados sensíveis no repositório.

- Documentação e apresentação: gerar documentação clara, checklist de segurança e vídeo explicativo demonstrando o sistema.

# Critérios de segurança exigido

- Encapsulamento adequado (atributos privados).
Validação de dados em todos os fluxos.

- Prevenção de injeções (SQL Injection e dados maliciosos).

- Tratamento de exceções sem expor informações internas.

- Uso correto do GitHub (sem senhas e códigos sensíveis).

- Checklist de segurança revisado antes da entrega final.

# Entregáveis

SPRINTS-1:

- ETAPA #1: Proposta de Projeto (tema, equipes e repositório GitHub) - 10/09.

- ETAPA #2: Diagrama de Classes inicial - 17/09.

- ETAPA #3: Diagrama de Sequência inicial - 24/09.

- ETAPA #4: Integração de modelos (Classes + Banco de Dados) - 01/10.


SPRINTS-2:

- ETAPA #5: Diagrama de Projeto (com abstrações e interfaces) - 22/10

- ETAPA #6: Integração Sequência + Colaboração - 29/10

- ETAPA #7: Diagrama de Estados - 05/11

- ETAPA #8: Projeto Final (código Java, documentação e vídeo demo) - 19/11.

# Contextualização

Uma cooperativa rural deseja monitorar plantações usando drones que realizam sobrevoos periódicos para coletar imagens e dados ambientais (temperatura, umidade e pragas).

# Funcionalidades mínimas a implementar

- Cadastro de áreas agrícolas (tamanho, localização e tipo de cultivo).

- Cadastro de drones (ID, sensores disponíveis e status).

- Agendamento de missões de voo (data, área e sensores a utilizar).

- Registro de dados coletados (imagens e valores de sensores simulados).

- Relatórios básicos sobre condições da plantação (últimas medições e voos realizados).

# Requisitos de segurança

- Controle de acesso diferenciado (administrador e operador de drone).

- Validação para impedir missões sobrepostas no mesmo drone.

- Tratamento seguro de dados de sensores (evitar entradas inválidas ou corrompidas).

- Checklist de segurança antes do voo (bateria mínima e sensores funcionando).


## Diagrama de Classes

O diagrama abaixo representa as principais entidades do sistema e seus relacionamentos.

![Diagrama de Classes](docs/DiagramaClassesInicial.png)


> O código-fonte deste diagrama pode ser encontrado em: [`docs/DiagramClassesInicial.puml`](docs/DiagramClassesInicial.puml)

## Diagrama de Sequência

Este diagrama detalha o fluxo de interações para o caso de uso "Agendar uma Nova Missão".

![Diagrama de Sequência](docs/DiagramaSequencialInicial.png)

> O código-fonte deste diagrama pode ser encontrado em: [`docs/DiagramaSequencialInicial.puml`](docs/DiagramaSequencialInicial.puml)

## Diagrama Banco de Dados

Este diagrama representa a estrutura das tabelas e relacionamentos do banco de dados SQL.

![Diagrama Banco de Dados](docs/DiagramaBancoDeDados.png)

> O código-fonte deste diagrama pode ser encontrado em: [`docs/DiagramaSequencialInicial.puml`](docs/DiagramaSequencialInicial.puml)




