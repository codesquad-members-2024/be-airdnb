<script>
    import profile_icon from "$lib/assets/image/profile_icon.svg";
</script>

<header class="flex flex-col mb-[3rem] relative z-20">
    <div id="logo" class="max-w-[1440px] mx-auto w-full flex items-center justify-between p-4 py-6">
        <a class="text-2xl font-semibold" href="/hosting">Airdnb</a>
        <nav class="hidden md:flex items-center gap-[30px] lg:gap-6">
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">투데이</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">달력</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">숙소</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting">메시지</a>
            <a class="duration-200 hover:text-airbnb-signature hover:cursor-pointer" href="/hosting/accommodations/create">숙소 등록</a>
        </nav>
        <button class="hidden md:flex">
            <img src="{profile_icon}" alt="User Profile Icon">
        </button>
        <button class="md:hidden grid place-items-center">
            <i class="fa-solid fa-bars"></i>
        </button>
    </div>
</header>