#!/bin/bash

# Cores para output
GREEN='\033[0;32m'
BLUE='\033[0;34m'
YELLOW='\033[1;33m'
RED='\033[0;31m'
NC='\033[0m' # No Color

# Função para mostrar uso
show_usage() {
    echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo -e "${BLUE}  LibShow - Script de Execução${NC}"
    echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
    echo ""
    echo "Uso: ./run.sh [opção]"
    echo ""
    echo "Opções:"
    echo "  (sem opção)  - Inicia backend e frontend juntos"
    echo "  backend      - Inicia apenas o backend"
    echo "  frontend     - Inicia apenas o frontend"
    echo "  help         - Mostra esta mensagem"
    echo ""
    echo "Exemplos:"
    echo "  ./run.sh                # Inicia tudo"
    echo "  ./run.sh backend        # Apenas backend"
    echo "  ./run.sh frontend       # Apenas frontend"
    echo ""
}

# Função de cleanup
cleanup() {
    echo ""
    echo -e "${RED}🛑 Parando todos os serviços...${NC}"
    if [ ! -z "$BACKEND_PID" ]; then
        kill $BACKEND_PID 2>/dev/null
    fi
    if [ ! -z "$FRONTEND_PID" ]; then
        kill $FRONTEND_PID 2>/dev/null
    fi
    exit 0
}

trap cleanup SIGINT SIGTERM

# Função para iniciar o backend
start_backend() {
    echo -e "${GREEN}📦 Iniciando Backend...${NC}"
    cd backend

    if [ ! -f "target/libshow-0.0.1-SNAPSHOT.jar" ]; then
        echo -e "${YELLOW}   Compilando projeto...${NC}"
        ./mvnw clean package -DskipTests -q
    fi

    java -jar target/libshow-0.0.1-SNAPSHOT.jar > ../backend.log 2>&1 &
    BACKEND_PID=$!
    cd ..

    echo -e "${GREEN}   ✅ Backend iniciado (PID: $BACKEND_PID)${NC}"
    echo -e "   📝 Logs: ${YELLOW}tail -f backend.log${NC}"
    echo -e "   🔌 URL: ${BLUE}http://localhost:8080${NC}"
    echo -e "   💾 H2: ${BLUE}http://localhost:8080/h2-console${NC}"
}

# Função para iniciar o frontend
start_frontend() {
    echo -e "${GREEN}🎨 Iniciando Frontend...${NC}"
    cd frontend

    if [ ! -d "node_modules" ]; then
        echo -e "${YELLOW}   Instalando dependências...${NC}"
        if command -v pnpm &> /dev/null; then
            pnpm install -s
        else
            npm install --silent
        fi
    fi

    if command -v pnpm &> /dev/null; then
        pnpm dev > ../frontend.log 2>&1 &
    else
        npm run dev > ../frontend.log 2>&1 &
    fi
    FRONTEND_PID=$!
    cd ..

    echo -e "${GREEN}   ✅ Frontend iniciado (PID: $FRONTEND_PID)${NC}"
    echo -e "   📝 Logs: ${YELLOW}tail -f frontend.log${NC}"
    echo -e "   🌐 URL: ${BLUE}http://localhost:5173${NC}"
}

# Main
echo ""

case "${1:-both}" in
    backend)
        start_backend
        echo ""
        echo -e "${GREEN}✨ Backend rodando!${NC}"
        echo -e "${YELLOW}Pressione Ctrl+C para parar${NC}"
        echo ""
        wait $BACKEND_PID
        ;;

    frontend)
        start_frontend
        echo ""
        echo -e "${GREEN}✨ Frontend rodando!${NC}"
        echo -e "${YELLOW}Pressione Ctrl+C para parar${NC}"
        echo ""
        wait $FRONTEND_PID
        ;;

    help|--help|-h)
        show_usage
        exit 0
        ;;

    both|*)
        echo -e "${BLUE}🚀 Iniciando LibShow (Backend + Frontend)...${NC}"
        echo ""

        start_backend
        echo ""

        echo -e "${YELLOW}⏳ Aguardando backend inicializar (15s)...${NC}"
        sleep 15
        echo ""

        start_frontend
        echo ""

        echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        echo -e "${GREEN}✨ LibShow está rodando!${NC}"
        echo ""
        echo -e "   🌐 Frontend: ${BLUE}http://localhost:5173${NC}"
        echo -e "   🔌 Backend:  ${BLUE}http://localhost:8080${NC}"
        echo -e "   💾 H2 Console: ${BLUE}http://localhost:8080/h2-console${NC}"
        echo ""
        echo -e "${YELLOW}📊 Ver logs em tempo real:${NC}"
        echo -e "   tail -f backend.log"
        echo -e "   tail -f frontend.log"
        echo ""
        echo -e "${YELLOW}Pressione Ctrl+C para parar todos os serviços${NC}"
        echo -e "${BLUE}━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━${NC}"
        echo ""

        wait
        ;;
esac
