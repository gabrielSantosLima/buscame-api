# Buscame API
<p>
<img src="https://img.shields.io/github/stars/gabrielSantosLima/buscame-api">
<img src="https://img.shields.io/github/forks/gabrielSantosLima/buscame-api">
<img src="https://img.shields.io/github/issues/gabrielSantosLima/buscame-api">
</p>
<p>
  Api que irá realizar o processamento e busca de produtos utilizando Kotlin e IA
</p>

## Tópicos
- <a href="#sobre">Sobre</a>
  - <a href="#introdução">Introdução</a>
  - <a href="#autores">Autores</a>
  - <a href="#instalação">Instalação</a>
- <a href="#funcionalidades">Objetivos</a>
- <a href="#rotas">Rotas</a>

## Sobre
### Introdução
A API de busca irá permitir buscar produtos por texto ou imagem utilizando os serviços em nuvem do [IBM Watson](https://cloud.ibm.com/) e da [plataforma em nuvem da Google](https://console.cloud.google.com/?hl=pt-BR). Os serviços utizados foram:
- Para a pesquisa por produtos: [Custom Search API](https://developers.google.com/custom-search/v1/overview)
- Para a análise de imagens: [Visual Recognition](https://www.ibm.com/br-pt/cloud/watson-visual-recognition)
- Para a tradução dos termos: [Language Translator](https://www.ibm.com/watson/services/language-translator/)

### Autores
<p>
  <img src="https://github.com/gabrielSantosLima.png" width=20 alt="Gabriel Lima">
  <a href="">Gabriel Lima</a>
</p>
<p>
  <img src="https://github.com/jonasjss.png" width=20 alt="Jonas Santos">
  <a href="">Jonas Santos</a>
</p>
<p>
  <img src="https://github.com/melinnediniz.png" width=20 alt="Melinne Diniz">
  <a href="">Melinne Diniz</a>
</p>
<p>
  <img src="https://github.com/sarahj315.png" width=20 alt="Sarah Júlia">
  <a href="">Sarah Júlia</a>
</p>

## Instalação

**Obs: Em ambiente de desenvolvimento**

- Clonar o projeto `git clone https://github.com/gabrielSantosLima/buscame-api`
- Criar credenciais em todos os serviços utilizados na API
 - [Custom Search API](https://cloud.google.com/docs/authentication/api-keys?hl=pt-BR&visit_id=637441536431321652-2520210197&rd=1)
 - [Visual Recognition e Language Translator](https://cloud.ibm.com/docs/watson?topic=watson-iam)
- Criar arquivo no diretório `src/main/resources/credentials.properties` com a seguinte estrutura:
```
#Visual Recognitions
VG_APIKEY=<CHAVE_DA_API>

#Custom Search API
CS_APIKEY=<CHAVE_DA_API>
CS_CONTEXTKEY=254b1bad154db7c85 # chave para teste do contexto de busca do robô de busca

#Language Translate
LT_APIKEY=<CHAVE_DA_API>

#Services URL
VG_URL=https://api.us-south.visual-recognition.watson.cloud.ibm.com
LT_URL=https://api.us-south.language-translator.watson.cloud.ibm.com
CS_URL=https://www.googleapis.com/customsearch/v1
```
- Executar projeto

## Funcionalidades
- Pesquisar produtos por imagem
- Pesquisar produtos por texto
- Aplicar alguns filtros de busca
  - Por preço
  - Por site
  - Por marca
- Busca paginada

## Rotas
### POST
- `/api/search/text?text=<TERMO_DE_BUSCA>`: busca por texto.
- `/api/search/image`: busca por imagem passando a imagem do tipo `ByteArray` no corpo da requisição .

### Parâmetros
- `brandName`: filtro de marca.
- `url`: filtro de url de site.
- `price`: filtro de preço.
- `page`: número da página de busca.

### Exemplo de Resultado
```json
[
{
  "id": "30337640",
  "term": "gato ",
  "title": "Moletom 284 Gato | Netshoes",
  "price": 229.99,
  "description": "Moletom 284 Gato | Netshoes",
  "url": "https://www.netshoes.com.br/moletom-284-gato-off+white-E97-0265-205",
  "image": "https://static.netshoes.com.br/produtos/moletom-284-gato/05/E97-0265-205/E97-0265-205_zoom1.jpg?ims=544x"
},
...
]
```
