import DeleteIcon from '@mui/icons-material/Delete';
import CreateIcon from '@mui/icons-material/Create';

const TodoItem = ({ todo, onDelete, onToggle, onEdit }) => {
  return (
    <div className=" tarefa-item m-2 p-3 bg-regal-violet rounded-2xl border-2 border-gray-950 flex items-center justify-between">
      <button
        className={`p-1 bg-cornflower-blue font-bold rounded-lg ${
          todo.completed ? 'hidden' : 'block'
        }`}
        onClick={() => onToggle(todo)}
        id={`status-button-${todo.id}`}
      >
        OK!
      </button>

      <p className="flex-1 text-center font-semibold">{todo.title}</p>

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
          onClick={() => onEdit(todo)}
          id={`edit-button-${todo.id}`}
        >
          <CreateIcon fontSize="medium" />
        </button>

        <button
          className="p-1 rounded-lg bg-b-shark text-white"
          onClick={() => onDelete(todo.id)}
          id={`delete-button-${todo.id}`}
        >
          <DeleteIcon fontSize="medium" />
        </button>
      </div>
    </div>
  );
};

export default TodoItem;
