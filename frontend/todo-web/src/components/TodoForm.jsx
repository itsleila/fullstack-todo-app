import { useState } from 'react';

const TodoForm = ({ onAdd }) => {
  const [text, setText] = useState('');

  return (
    <div className="flex justify-center flex-wrap mb-4">
      <input
        type="text"
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Digite uma nova tarefa"
        className="flex-1 p-3 border-2 border-gray-950 rounded-2xl focus:outline-none mr-2 mb-2"
        id="newTodo-input"
      />

      <button
        onClick={() => {
          onAdd(text);
          setText('');
        }}
        className="w-full p-2 bg-regal-violet text-white font-bold rounded-lg hover:bg-fuchsia-950 sm:w-auto"
        id="newTodo-button"
      >
        Adicionar
      </button>
    </div>
  );
};

export default TodoForm;
