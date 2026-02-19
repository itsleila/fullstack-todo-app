/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,jsx,ts,tsx}'],
  theme: {
    extend: {
      colors: {
        'regal-blue': '#60c0bf',
        'cornflower-blue': '#517eed',
        'regal-violet': '#b380da',
        'b-shark': '#1a1c1e',
      },
    },
  },
  plugins: [],
};
