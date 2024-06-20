<script>
  import { Route } from 'tinro';
  import Main from './pages/Main.svelte';
    import Admin from './pages/Admin.svelte';
</script>

<Route path="/"><Main /></Route>
<Route path="/admin"><Admin /></Route>