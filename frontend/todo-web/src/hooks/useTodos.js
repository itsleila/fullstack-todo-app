import { useEffect, useState } from 'react';
import fetchTodos from '../infra/todoApi.js';
import { createToDo, removeToDo, updateToDo } from '../services/todoServices';

export const useTodos = () => {
  const [toDos, setToDos] = useState([]);
  const [loading, setLoading] = useState(true);

  const carregarLista = async () => {
    try {
      setLoading(true);
      const data = await fetchTodos();
      setToDos(data);
      return {};
    } catch {
      return {
        error: 'Erro ao carregar tarefas, por favor tente novamente mais tarde',
      };
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarLista();
  }, []);

  const adicionar = async (titulo) => {
    if (!titulo.trim()) {
      return { error: 'A tarefa não pode estar vazia' };
    }

    const novo = await createToDo(titulo);

    if (!novo.error) {
      setToDos((prev) => [...prev, novo]);
      return { success: true };
    }

    return { error: novo.error };
  };

  const remover = async (id) => {
    const res = await removeToDo(id);

    if (!res?.error) {
      setToDos((prev) => prev.filter((t) => t.id !== id));
      return { success: true };
    }

    return { error: res.error };
  };

  const atualizar = async (id, data) => {
    const atualizado = await updateToDo(id, data);

    if (!atualizado.error) {
      setToDos((prev) => prev.map((t) => (t.id === id ? atualizado : t)));
      return { success: true };
    }

    return { error: atualizado.error };
  };

  return {
    toDos,
    loading,
    carregarLista,
    adicionar,
    remover,
    atualizar,
  };
};
