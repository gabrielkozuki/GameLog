# GameLog

## Introdução

GameLog é um aplicativo Android desenvolvido em Kotlin com Jetpack Compose que permite aos usuários gerenciar sua biblioteca pessoal de jogos. Além disso, também é possível criar listas personalizadas (jogatinas) e escrever reviews.

### Principais funcionalidades:
- Biblioteca de Jogos: organize seus jogos por status (Jogando, Backlog, Lista de Desejos, Finalizado, Abandonado)
- Busca de Jogos: busque jogos e adicione-os à biblioteca utilizando a API RAWG
- Reviews: crie e gerencie reviews personalizados dos seus jogos
- Jogatinas: crie listas customizadas de jogos para diferentes propósitos, como começar uma saga ou listar seus jogos favoritos de 2025
- Autenticação (e-mail/senha e Google Sign-In) e edição de perfil do usuário, incluindo upload de foto
- Dados salvos na nuvem

### Tecnologias Utilizadas:
- Kotlin com Jetpack Compose: linguagem de programação e UI
- Firebase Authentication: autenticação de usuários
- Firebase Realtime Database: banco de dados
- Appwrite Storage: cloud storage para upload de imagens de perfil
- Retrofit: consumo da API RAWG
- Coil: carregamento de imagens
- Lottie: animações

---

## Instalação

### Pré-requisitos:
- Android Studio com SDK Android 24 ou superior
- Conta no Firebase
- Chave da API RAWG (acesse [RAWG.io](https://rawg.io/apidocs) e crie uma conta)

1. **Clone o repositório:**
   ```bash
   git clone https://github.com/gabrielkozuki/GameLog.git
   cd GameLog
   ```

2. **Configure o Firebase:**
   - Acesse o [Firebase Console](https://console.firebase.google.com/)
   - Crie um novo projeto ou use um existente
   - Adicione um aplicativo Android com o package name: `com.example.gamelog`
   - Baixe o arquivo `google-services.json` e coloque o arquivo na pasta `app/`
   - Ative o **Firebase Authentication** (Email/Password e Google Sign-In)
   - Ative o **Firebase Realtime Database**
   - Obtenha o `GOOGLE_CLIENT_ID` em: Authentication > Método de login > Google (Editar configuração) > Configuração do SDK da Web)

3. **Configure as variáveis de ambiente:**
   - Crie o arquivo `local.properties` na raíz do projeto
   - Adicione as seguintes linhas:
   ```properties
   RAWG_API_URL="https://api.rawg.io/api/"
   RAWG_API_KEY="sua_chave"
   GOOGLE_CLIENT_ID="seu_google_client_id"
   ```

4. **Configurar Appwrite Storage:**
   - Acesse o [Appwrite Console](https://cloud.appwrite.io/)
   - Crie um novo projeto ou use um existente
   - Copie o APPWRITE_PROJECT_ID e o APPWRITE_URL do painel do projeto
   - Acesse Storage e crie um novo bucket para as fotos de perfil
   - Copie o ID gerado e cole em APPWRITE_BUCKET_ID
   - Adicione também esses valores ao seu arquivo local.properties:
     ```properties
     APPWRITE_URL="https://sfo.cloud.appwrite.io/v1"
     APPWRITE_PROJECT_ID="seu_project_id"
     APPWRITE_BUCKET_ID="seu_bucket_id"
     ```

Após isso o projeto poderá ser executado no Android Studio.

Caso dê erro ao fazer upload da foto:
- No painel do Appwrite, vá em Overview -> Integrations -> Add platform e selecione Android
- Em Name adicione "GameLog" e em Package name adicione "com.example.gamelog"
- Clique em Create Platform e deixe a janela aberta. Em seguida, tente fazer upload da foto novamente


---

## Estrutura do Projeto

```
app/src/main/java/com/example/gamelog/
├── base/                   # Componentes base do app
│   ├── CallScaffold.kt    # Gerenciamento de scaffolds e telas
│   ├── MainActivity.kt    # Activity principal
│   ├── Navigation.kt      # Configuração de navegação
│   ├── NavItem.kt        # Items da navegação do app
│   └── Routes.kt         # Definição de rotas
├── data/                  # Camada de dados
│   ├── api/              # Serviços de API
│   ├── model/            # Modelos de dados Game, GameDetail, Review
│   ├── repository/       # Repositórios de dados, fazem a conexão com o Firebase
│   ├── service/          # Configuração do Appwrite e classe Service de upload de imagem
│   └── util/             
├── screens/              # Telas do aplicativo (screens e viewmodels)
│   ├── app/
│   │   ├── account/     # Telas de conta/perfil
│   │   ├── gamelib/     # Biblioteca de jogos
│   │   ├── gamelist/    # Jogatinas
│   │   └── review/      # Reviews
│   └── login/           # Autenticação
└── ui/                  # Componentes de UI
    ├── components/      # Componentes reutilizáveis
    └── theme/          # Tema e estilos
```
