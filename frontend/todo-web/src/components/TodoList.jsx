import { useState } from 'react';
import { useTodos } from '../hooks/useTodos';
import TodoItem from './TodoItem';
import TodoForm from './TodoForm';

import {
  Alert,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';

const TodoList = () => {
  const { toDos, loading, adicionar, remover, atualizar } = useTodos();

  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);

  const [editando, setEditando] = useState(null);
  const [novoTitulo, setNovoTitulo] = useState('');

  const showMessage = (setter, message) => {
    setter(message);
    setTimeout(() => setter(null), 3000);
  };

  const handleAdd = async (text) => {
    const res = await adicionar(text);

    if (res.error) {
      showMessage(setError, res.error);
    } else {
      showMessage(setSuccess, 'Sua nova tarefa foi adicionada!');
    }
  };

  const handleDelete = async (id) => {
    const res = await remover(id);

    if (res.error) {
      setError(res.error);
    } else {
      showMessage(setSuccess, 'Tarefa removida com sucesso!');
    }
  };

  const handleToggle = async (todo) => {
    const res = await atualizar(todo.id, {
      ...todo,
      completed: !todo.completed,
    });

    if (res.error) {
      setError(res.error);
    } else {
      showMessage(setSuccess, 'Tarefa atualizada com sucesso!');
    }
  };

  const handleEdit = (todo) => {
    setEditando(todo);
    setNovoTitulo(todo.title);
  };

  const handleSaveEdit = async () => {
    const res = await atualizar(editando.id, {
      ...editando,
      title: novoTitulo,
    });

    if (res.error) {
      setError('Erro ao atualizar tarefa');
    } else {
      setEditando(null);
      showMessage(setSuccess, 'Tarefa editada com sucesso!');
    }
  };

  return (
    <>
      <div className="bg-regal-blue max-w-3xl p-5 rounded-xl mx-auto">
        <h2 className="text-3xl font-bold m-1 p-3">TO-DO List:</h2>

        <TodoForm onAdd={handleAdd} />

        {loading && (
          <div className="flex justify-center mt-4">
            <CircularProgress size="30px" color="secondary" />
          </div>
        )}

        {error && (
          <Alert severity="error" className="error-alert">
            {error}
          </Alert>
        )}
        {success && (
          <Alert severity="success" className="success-alert">
            {success}
          </Alert>
        )}

        {toDos.map((todo) => (
          <TodoItem
            key={todo.id}
            todo={todo}
            onDelete={handleDelete}
            onToggle={handleToggle}
            onEdit={handleEdit}
          />
        ))}
      </div>

      <Dialog open={!!editando} onClose={() => setEditando(null)}>
        <DialogTitle>Edite sua tarefa</DialogTitle>

        <DialogContent>
          <input
            id="edit-input"
            type="text"
            value={novoTitulo}
            onChange={(e) => setNovoTitulo(e.target.value)}
            className="w-full p-2 border rounded"
          />
        </DialogContent>

        <DialogActions>
          <button onClick={() => setEditando(null)}>Cancelar</button>
          <button onClick={handleSaveEdit} id="save-button">
            Salvar
          </button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default TodoList;
