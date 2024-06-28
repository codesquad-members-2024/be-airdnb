<script>
    import AdminLogin from "../components/admin/AdminLogin.svelte";
    import AdminRegister from "../components/admin/AdminRegister.svelte";


    let showLogin = false;
    let showRegister = false;

    function closeLogin() {
        showLogin = false;
    }

    function closeRegister() {
        showRegister = false;
    }
    
</script>

<div class="flex items-center justify-center min-h-screen bg-gray-100">
  <div class="space-y-4">
    <h1 class="text-2xl font-bold text-center">Admin Portal 🔑</h1>
    <div class="flex space-x-4">
      <button
        class="px-4 py-2 text-sm font-medium text-white bg-blue-600 border border-transparent rounded-md shadow-sm hover:bg-blue-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-blue-500"
        on:click={() => showLogin = true}
      >
        로그인
      </button>
      <button
        class="px-4 py-2 text-sm font-medium text-white bg-green-600 border border-transparent rounded-md shadow-sm hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500"
        on:click={() => showRegister = true}
      >
        회원가입
      </button>
    </div>
  </div>

  {#if showLogin}
    <AdminLogin close={closeLogin} />
  {/if}

  {#if showRegister}
    <AdminRegister close={closeRegister} />
  {/if}
</div>