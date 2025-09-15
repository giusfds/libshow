import React, { useState, useRef, type FormEvent } from 'react';
import { Link } from 'react-router-dom';

interface AuthFormProps {
  isLogin: boolean;
}

const getApiEndpoint = (isLogin: boolean): string => {
  return isLogin ? "/api/login" : "/api/signup";
};

const AuthForm: React.FC<AuthFormProps> = ({ isLogin }) => {
  const emailRef = useRef<HTMLInputElement>(null);
  const nameRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const roleRef = useRef<HTMLSelectElement>(null);

  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const handleSubmit = async (event: FormEvent) => {
    event.preventDefault();
    setIsLoading(true);
    setError(null);

    const email = emailRef.current?.value;
    const password = passwordRef.current?.value;
    const name = nameRef.current?.value;
    const role = roleRef.current?.value;

    const userData = isLogin 
      ? { email, password }
      : { email, password, name, role };
    
    try {
      const endpoint = getApiEndpoint(isLogin);
      const response = await fetch(endpoint, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(userData),
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.message || 'Ocorreu um erro.');
      }

      console.log('Sucesso:', data);
    } catch (err: any) {
      console.error("Ocorreu um erro na busca dos dados:", err);
      setError(err.message || 'Não foi possível conectar ao servidor. Tente novamente.');
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <form className="auth-form" onSubmit={handleSubmit}>
      <h1 className="auth-form-title">{isLogin ? 'Bem-vindo de volta' : 'Junte-se a nós'}</h1>
      <p className="auth-form-subtitle">{isLogin ? 'Faça login para continuar sua leitura.' : 'Crie sua conta e descubra novos livros.'}</p>
      
      <div className="input-group">
        <label htmlFor="email">E-mail</label>
        <input type="email" id="email" placeholder="vini@show.com" ref={emailRef} required />
      </div>

      {!isLogin && (
        <div className="input-group">
          <label htmlFor="name">Nome Completo</label>
          <input type="text" id="name" placeholder="Seu nome" ref={nameRef} required />
        </div>
      )}

      <div className="input-group">
        <label htmlFor="password">Senha</label>
        <input type="password" id="password" placeholder="***********" ref={passwordRef} required />
      </div>

      {!isLogin && (
        <div className="input-group">
          <label htmlFor="role">Função</label>
          <select id="role" ref={roleRef} required>
            <option value="">Selecione sua função</option>
            <option value="librarian">Bibliotecário</option>
            <option value="user">Usuário</option>
          </select>
        </div>
      )}

      {error && <p className="error-message">{error}</p>}

      <button type="submit" className="primary-button" disabled={isLoading}>
        {isLoading ? 'Aguarde...' : (isLogin ? 'Entrar na conta' : 'Criar conta')}
      </button>
      
      <Link to={isLogin ? "/signup" : "/login"} className="secondary-link">
        {isLogin ? 'Não tem uma conta? Crie uma.' : 'Já tem uma conta? Faça login.'}
      </Link>
    </form>
  );
};

export default AuthForm;