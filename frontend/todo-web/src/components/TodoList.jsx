import { useEffect, useState } from 'react';
import fetchTodos from '../infra/todoApi.js';
import { createToDo, removeToDo, updateToDo } from '../services/todoServices';
import {
  Alert,
  CircularProgress,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
} from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import CreateIcon from '@mui/icons-material/Create';

const TodoList = () => {
  const [toDos, setToDos] = useState([]);
  const [novaTarefa, setNovaTarefa] = useState('');
  const [error, setError] = useState(null);
  const [success, setSuccess] = useState(null);
  const [loading, setLoading] = useState(true);
  const [editandoId, setEditandoId] = useState(null);
  const [novoTitulo, setNovoTitulo] = useState('');

  const carregarLista = async () => {
    try {
      setLoading(true);
      setError(null);
      const todos = await fetchTodos();
      setToDos(todos);
    } catch (err) {
      setError(
        'Erro ao carregar tarefas, por favor tente novamente mais tarde',
      );
      setToDos([]);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    carregarLista();
  }, []);

  const handleCompletedChange = async (id) => {
    const todoAtual = toDos.find((t) => t.id === id);

    const atualizado = await updateToDo(id, {
      ...todoAtual,
      completed: !todoAtual.completed,
    });

    if (!atualizado.error) {
      setToDos((todos) =>
        todos.map((todo) => (todo.id === id ? atualizado : todo)),
      );
      setSuccess('Tarefa atualizada com sucesso!');
      setTimeout(() => setSuccess(null), 3000);
    } else {
      setError(atualizado.error);
    }
  };

  const handleRemover = async (id) => {
    const resultado = await removeToDo(id);

    if (!resultado?.error) {
      setToDos((todoAntg) => todoAntg.filter((todo) => todo.id !== id));

      setSuccess('Tarefa removida com sucesso!');
      setTimeout(() => setSuccess(null), 3000);
    } else {
      setError(resultado.error);
    }
  };

  const handleAdicionar = (novaTarefa) => {
    if (novaTarefa.trim() === '') {
      setError('A tarefa não pode estar vazia');
      setTimeout(() => setError(null), 3000);
      return;
    }
    createToDo(novaTarefa).then((novoTodo) => {
      if (!novoTodo.error) {
        setToDos((todosAntigos) => [...todosAntigos, novoTodo]);
        setNovaTarefa('');
        setSuccess('Sua nova tarefa foi adicionada!');
        setTimeout(() => setSuccess(null), 3000);
      } else {
        setError(novoTodo.error);
      }
    });
  };

  const handleOpenModal = (id) => {
    const todo = toDos.find((t) => t.id === id);
    setNovoTitulo(todo.title);
    setEditandoId(id);
  };

  const handleSalvarEdicao = async () => {
    try {
      const todoAtual = toDos.find((t) => t.id === editandoId);
      const atualizado = await updateToDo(editandoId, {
        ...todoAtual,
        title: novoTitulo,
      });

      setToDos((todos) =>
        todos.map((todo) => (todo.id === editandoId ? atualizado : todo)),
      );
      setEditandoId(null);
      setSuccess('Tarefa editada com sucesso!');
      setTimeout(() => setSuccess(null), 3000);
    } catch (err) {
      setError('Erro ao atualizar tarefa');
    }
  };

  return (
    <>
      <div className="bg-regal-blue max-w-3xl p-5 rounded-xl mx-auto">
        <h2 className="text-3xl font-bold m-1 p-3 font-sans">TO-DO List:</h2>
        <div className="flex justify-center flex-wrap mb-4 ">
          <input
            type="text"
            value={novaTarefa}
            onChange={(e) => setNovaTarefa(e.target.value)}
            placeholder="Digite uma nova tarefa"
            id="newTodo-input"
            className="flex-1 p-3 border-2 border-gray-950 rounded-2xl focus:outline-none focus:ring-2 focus:ring-regal-violet mr-2 mb-2"
          />
          <button
            onClick={() => handleAdicionar(novaTarefa)}
            className="w-full p-2 bg-regal-violet text-white font-bold rounded-lg hover:bg-fuchsia-950 transition-colors duration-300 sm:w-auto"
            id="newTodo-button"
          >
            Adicionar
          </button>
        </div>
        {loading && (
          <div className="flex justify-center mt-4 mx-auto">
            <CircularProgress size="30px" color="secondary" />
          </div>
        )}
        {error && (
          <div className="error-alert">
            <Alert
              severity="error"
              onClose={() => setError(null)}
              className="mt-4"
            >
              {error}
            </Alert>
          </div>
        )}
        {success && (
          <div className="success-alert">
            <Alert
              severity="success"
              onClose={() => setSuccess(null)}
              className="mt-4"
            >
              {success}
            </Alert>
          </div>
        )}
        {toDos.map((todo) => (
          <div
            key={todo.id}
            className="m-2 p-3 bg-regal-violet rounded-2xl border-2 border-gray-950 flex items-center justify-between tarefa-item "
          >
            <button
              className={`p-1 bg-cornflower-blue font-bold rounded-lg ${
                todo.completed ? 'hidden' : 'block'
              }`}
              onClick={() => handleCompletedChange(todo.id)}
              id={`status-button-${todo.id}`}
            >
              OK!
            </button>
            <p
              id={`todo-title-${todo.id}`}
              className="flex-1 text-center font-semibold"
            >
              {todo.title}
            </p>
            <div className="flex items-center space-x-2">
              <span
                id="status-text"
                className={
                  todo.completed
                    ? 'bg-green-400 p-1 rounded-lg'
                    : 'bg-red-400 p-1 rounded-lg'
                }
              >
                {todo.completed ? 'Feito' : 'A Fazer'}
              </span>
              <button
                className="p-1 rounded-lg bg-b-shark text-white"
                onClick={() => handleOpenModal(todo.id)}
                id={`edit-button-${todo.id}`}
              >
                <CreateIcon fontSize="medium" />
              </button>
              <button
                className="p-1 rounded-lg bg-b-shark text-white"
                onClick={() => handleRemover(todo.id)}
                id={`delete-button-${todo.id}`}
              >
                <DeleteIcon fontSize="medium" />
              </button>
            </div>
          </div>
        ))}
      </div>
      <Dialog
        open={editandoId !== null}
        onClose={() => setEditandoId(null)}
        fullWidth
        maxWidth="sm"
      >
        <DialogTitle>Edite sua tarefa</DialogTitle>

        <DialogContent>
          <input
            id="edit-input"
            type="text"
            placeholder="Novo título"
            value={novoTitulo}
            onChange={(e) => setNovoTitulo(e.target.value)}
            className="w-full p-2 border border-gray-300 rounded"
          />
        </DialogContent>

        <DialogActions>
          <button
            id="cancel-button"
            onClick={() => setEditandoId(null)}
            className="p-2 bg-red-400 text-white rounded hover:bg-gray-500"
          >
            Cancelar
          </button>

          <button
            id="save-button"
            onClick={handleSalvarEdicao}
            className="p-2 bg-regal-violet text-white rounded hover:bg-fuchsia-950"
          >
            Salvar
          </button>
        </DialogActions>
      </Dialog>
    </>
  );
};

export default TodoList;
