# Frontend - React + TypeScript + SWC

Este é o frontend do projeto, desenvolvido com **React**, **TypeScript** e **Vite** (com SWC para compilação rápida).

---

## Pré-requisitos

Antes de começar, você precisa ter instalado:

* [Node.js](https://nodejs.org/) (versão 18 ou superior recomendada)
* [npm](https://www.npmjs.com/get-npm) (geralmente já vem com Node.js)

---

## Passo a passo para rodar o projeto

1. **Clonar o repositório**

   ```bash
   git clone <URL_DO_REPOSITORIO>
   cd <PASTA_DO_REPOSITORIO>
   ```

2. **Entrar na pasta do frontend**

   ```bash
   cd frontend
   ```

3. **Instalar as dependências**

   ```bash
   npm install
   ```

   Isso vai baixar todas as dependências necessárias para o projeto rodar.

4. **Rodar o servidor de desenvolvimento**

   ```bash
   npm run dev
   ```

   * Depois de rodar, você verá algo como:

     ```
     Local: http://localhost:5173/
     ```
   * Abra esse link no navegador para ver a aplicação funcionando.

---

## Rodar build de produção (opcional)

1. **Gerar a build final**

   ```bash
   npm run build
   ```

2. **Visualizar a build localmente**

   ```bash
   npm run preview
   ```

   * Isso vai abrir a versão de produção da aplicação em `http://localhost:4173/` (ou outra porta mostrada no terminal).

---

## Estrutura do projeto

```
frontend/
  ├─ src/           # Código-fonte da aplicação
  ├─ public/        # Arquivos estáticos
  ├─ package.json   # Dependências e scripts do projeto
  └─ vite.config.ts # Configuração do Vite
```

---

## Observações

* Certifique-se de estar usando Node.js e npm compatíveis.
* Se aparecer algum erro de comando não encontrado (`vite: command not found`), rode `npm install` novamente dentro da pasta `frontend`.
