# CSTV
## Descrição
Este app lista todas as partidas atuais e futuras de CS:GO com informações de times, horário e liga, conforme disponibilizado pela API do [PandaScore](https://developers.pandascore.co/docs), 
também permite selecionar uma partida específica para verificar os jogadores de cada time.

## Executar o projeto
É necessário ter instalado o [Git](https://git-scm.com/) e o [Android Studio](https://developer.android.com/studio) configurado.

Clone o repositório para a pasta desejada com 
```bash
$ git clone https://github.com/Joaocosmo/CSTV
```
No Android Studio abra o projeto clonado, aperte em Sync Now e quando finalizar o sync, com dispositivo conectado ou emulador configurado aperte em Run 'app' na barra de ferramentas.

## Bibliotecas
O aplicativo utiliza a biblioteca [Glide](https://bumptech.github.io/glide/) para carregar imagens externas, [Retrofit](https://square.github.io/retrofit/) para comunicar com a API, 
[Gson](https://github.com/google/gson) para converter a resposta da API de Json para classes nativas,
[Logging Interceptor](https://github.com/square/okhttp/tree/master/okhttp-logging-interceptor) apenas para fins de debug para listar as requisições e respostas no log, e bibliotecas do [Android Jetpack](https://developer.android.com/jetpack/androidx/explorer?gclid=Cj0KCQjw0PWRBhDKARIsAPKHFGg1spKQZuAwQdZ1kzALkPlrRRJjWErjAqqvtRWRyduAAoosC_mTZzUaApnyEALw_wcB&gclsrc=aw.ds&case=all) como de navegação, splash screen, e LiveData entre outras.
## Estrutura MVVM
A arquitetura do projeto foi feita em MVVM, os arquivos de Service fazem a comunicação direta com a API e com o Retrofit, na configuração da biblioteca foi adicionado 
o Logging Interceptor para log das comunicações com a API, Gson para converter as respostas nos Data Classes dos arquivos de Response, e um interceptador para adicionar a chave de API nas requisições.

Os arquivos de Repository utilizam o service para gerar dados úteis para as interfaces, nesse app o Repository gera variáveis de estado com Flow, que são transmitidas para o ViewModel, 
uma das rotas é paginada então há um arquivo de Paging Source, para gerar um fluxo de páginas.

Os ViewModels são encarregados de mediar os dados provenientes dos Repository, e os fragmentos utilizam as informações do ViewModel para atualizar as Views.

## Pontos importantes
- A listagem de partidas usa paginação para pegar as partidas da API em páginas, sem precisar baixar todas de uma vez, e possui uma função de refresh com swipe no topo da lista.
- As imagens não ficam limitadas ao círculo por motivos estéticos para evitar cropping, apenas as imagens de jogadores tem suas bordas arredondadas.
- A chamada à rota de listagem de partidas usa um range de "not_started" até "running", que funciona para receber todas as partidas necessárias para o app, porém como esse range é em ordem alfabética, também retorna "postponed", que são descartados, e usa um sort para listar em ordem do parâmetro "begin_at".
- 
