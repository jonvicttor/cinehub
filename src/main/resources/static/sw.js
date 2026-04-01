// Service Worker básico para garantir que o PWA seja instalável
const CACHE_NAME = 'cinehub-pwa-v1';

self.addEventListener('install', (event) => {
    console.log('[Service Worker] Instalado');
    self.skipWaiting();
});

self.addEventListener('activate', (event) => {
    console.log('[Service Worker] Ativado');
    event.waitUntil(clients.claim());
});

// Intercepta as requisições (obrigatório para o PWA passar no teste do Chrome)
self.addEventListener('fetch', (event) => {
    // Por enquanto, não vamos interceptar a rede para não atrapalhar o seu banco de dados
    // Mas o evento precisa existir para o Chrome liberar o botão de "Instalar App"
});