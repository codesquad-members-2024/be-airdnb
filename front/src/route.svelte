<script>
    import { Route } from 'tinro';
    import Main from "./pages/Main.svelte";
    import StaysList from "./pages/StaysList.svelte";
    import Login from "./pages/Login.svelte";

</script>

<Route path="/"><Main /></Route>
<Route path="/stays"><StaysList /></Route>
<Route path="/login"><Login /></Route>