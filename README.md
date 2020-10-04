<h1 align="center">
  <br>
  <img src="https://github.githubassets.com/images/modules/logos_page/Octocat.png" alt="GithubApp Logo" width="200">
  <br>
GithubApp
  <br>
</h1>

<h4 align="center">O GithubApp é uma aplicação simples e intuitiva que te permite visualizar e buscar repositórios públicos do Github. 


<p align="center">
  <a href="#demo">Demo</a> -
  <a href="#ferramentas">Ferramentas</a> -
  <a href="#features">Features</a> -
  <a href="#como-usar">Como usar</a> -
  <a href="#feedback">Feedback</a> -
  <a href="#autora">Autora</a> -
  <a href="#licença">Licença</a>
</p>

## Demo

<div>
<img src="https://github.com/jamilelima/github-app/blob/main/assets/githubapp.gif?raw=true" alt="Gif of Githubapp" width="200"/>
<div>

## Ferramentas

O aplicativo foi criado com as seguintes ferramentas:

* MVVM
    > O MVVM é uma peça fundamental da arquitetura desse app. Com ele consigo tratar efeitos do ciclo de vida do android, mantenho o binding da view com os dados atualizados e também me proporciona mais facilidade na hora de criar os testes unitários. 

* Koin
    > Optei por utilizar o koin como framework para injeção de dependência pela sua facilidade de implementação e também pela minha experiência anterior com ele.

* LiveData e MutableLiveData
    > Utilizo esses observáveis por serem lifecycle-aware, pois isso garante que a atualização seja feita só nos componentes que estão ativos no clico de vida.


* Navigation Component
    > Um dos meus pontos preferidos do Architecture Components é o navigation. Antes dele, lidar com o vários fragmentos, era bem complicado. Salvar todas esses fluxos, os argumentos passados e os possíveis erros de `IllegalStateException` que ocorriam dava muita dor de cabeça. Agora com o navigation lidar com back stack, exceptions e etc. ficou muito mais fácil, além de dar uma visão melhor da navegação com o grafo do fluxo. 

* Mockk
  >  Escolhi o mockk por ser escrito em Kotlin e, por consequência, suportar extensions, final classes, singleton e outras coisas que facilitam mockar os recursos para testes mais sustentáveis.


* Coroutines e Flow
  > Para lidar com chamadas assíncronas escolhi usar as coroutines que facilitam bastante nosso dia a dia por não bloquar a thread em que executamos uma operação mas sim dar a possibilidade de suspender a operação a qualquer momento.
  Seguindo no mesmo pensamento, escolhi usar Flow por ter total suporte as `suspend functions` e também por conseguir lidar melhor com o stream de dados sequenciais assíncronamente.

* Retrofit
  > De longe é a biblioteca de networking que tive mais contato e também é a que vejo sendo mais usada pela comunidade. Escolhi usá-la nesse projeto por ser simples de usar, basicamente só preciso dizer a URL e o que/como eu espero essa resposta (que no meu caso estou lidando com o Gson). Além disso tudo, a documentação é ótima e a comunidade é enorme. 

* Glide
  > Além de ser uma biblioteca recomendada pelo Google, gosto muito praticidade ao usar o glide. Por exemplo, neste app eu consegui deixar a imagem em um shape circular sem muitos problemas, apenas chamando um método ao fazer o setup da imagem. Também confesso que essa é a biblioteca que tive maior tempo de experiência usando.

## Features

Aqui algumas coisas que você já pode encontrar nesse app:

* Visualizar lista de repositórios públicos da API do github
* Pesquisa de repositórios públicos
* Visualizar detalhes de um repositório


## Como usar

* Faça o clone do repositório
  * `$ git clone git@github.com:jamilelima/github-app.git`
* Abra o app no android studio e faça as sincronizações necessárias
* Faça o build do app
* O app vai ser instalado no seu dispositivo ou no emulador
* Aproveite :tada:


## Feedback

Sinta-se a vontade para abrir uma [issue](https://github.com/jamilelima/github-app/issues/new) com dúvidas e/ou sugestões!


## Autora

[Jamile Lima](https://jamile.dev)

---

## Licença

MIT License

Copyright (c) 2020 Jamile Lima
