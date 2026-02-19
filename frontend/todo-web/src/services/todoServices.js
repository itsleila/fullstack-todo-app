import { API_URL } from '../config/api.js';

export async function removeToDo(id) {
  return fetch(`${API_URL}/todos/${id}`, {
    method: 'DELETE',
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Erro ao deletar tarefa');
      }
      return true;
    })
    .catch((error) => ({ error: error.message }));
}

export async function updateToDo(id, atualizado) {
  return fetch(`${API_URL}/todos/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(atualizado),
  })
    .then((response) => {
      if (!response.ok) {
        throw new Error('Erro ao atualizar tarefa');
      }
      return response.json();
    })
    .catch((error) => ({ error: error.message }));
}

export async function createToDo(novaTarefa) {
  try {
    const response = await fetch(`${API_URL}/todos`, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify({
        title: novaTarefa,
        completed: false,
      }),
    });
    if (!response.ok) {
      throw new Error('Erro ao criar tarefa');
    }
    return await response.json();
  } catch (error) {
    return { error: error.message };
  }
}
