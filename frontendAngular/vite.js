import { defineConfig } from 'vite';

export default defineConfig({
  assetsInclude: ['**/*.gz', '**/*.br', '**/*.wasm'],
  worker: {
    format: 'es'
  }
});