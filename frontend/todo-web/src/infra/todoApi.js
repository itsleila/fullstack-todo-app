import { API_URL } from '../config/api.js';

export default async function fetchTodos() {
  const url = `${API_URL}/todos`;

  try {
    const controller = new AbortController();
    const timeoutId = setTimeout(() => controller.abort(), 5000);
    const response = await fetch(url, { signal: controller.signal });
    clearTimeout(timeoutId);
    if (!response.ok) {
      throw new Error('Erro ao buscar tarefas');
    }
    return await response.json();
  } catch (error) {
    if (error.name === 'AbortError') {
      throw new Error(
        'A resposta para requisição demorou demais. Por favor, tente novamente mais tarde.',
      );
    }
    throw new Error('Não foi possível conectar com à API');
  }
}
