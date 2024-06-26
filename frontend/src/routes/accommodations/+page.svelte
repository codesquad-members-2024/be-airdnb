<script>
    import AccoCard from "./AccoCard.svelte";
    import profile_icon from "$lib/assets/image/profile_icon.svg";
    import LoginModal from "./LoginModal.svelte";
    import ReservationModal from "./ReservationModal.svelte";
    import ProfilePopup from "../../components/ProfilePopup.svelte";
    import {isLoggedIn} from "../../store/Auth.js";
    import {accoProducts} from "../../store/AccoProducts.js";
    import MiniSearchBar from "../../components/filtermodal/MiniSearchBar.svelte";
    import MapSection from "./MapSection.svelte";
    import {reservation} from "../../store/AccoProducts.js";
    import {filter} from "../../store/Filter.js";

    $: isLoginBtnClicked = false;
    const handleLoginBtnClick = () => {
        isLoginBtnClicked = !isLoginBtnClicked;
    }

    $: isProfileBtnClicked = false;
    const handleProfileBtnClick = () => {
        isProfileBtnClicked = !isProfileBtnClicked;
    }
    //
    // let accommodations = [{
    //     accoId: 1,
    //     placeCategory: "간단한 지역명 + 숙소 카테고리",
    //     title: "숙소 제목",
    //     maxGuestCount: 3,
    //     bedroomCount:1 ,
    //     bedCount:1,
    //     bathroomCount:1,
    //     amenities: ["주방", "무선 인터넷", "에어컨", "헤어드라이어"],
    //     totalPrice: 1729707,
    // },
    //     {
    //         accoId: 2,
    //         placeCategory: "서울 온천",
    //         title: "온천 가고 싶다",
    //         maxGuestCount: 10,
    //         bedroomCount: 10,
    //         bedCount: 100,
    //         bathroomCount:100,
    //         amenities: ["주방", "무선 인터넷", "에어컨", "헤어드라이어"],
    //         totalPrice: 100000000000,
    //     }
    // ]

    $: isReservationModalOpened = false;
    const handleReservationModalClicked = () => {
        isReservationModalOpened = !isReservationModalOpened;
    }

    let accommodations = [];
    $: accommodations = $accoProducts.accoProductList;

    function handleAccoSelected(event) {
        const {accoId} = event.detail;
        const selectedAcco = accommodations.find(acco => acco.accoId === accoId);
        if (selectedAcco) {
            reservation.updateReservationModal({
                title: selectedAcco.title,
                accoId: selectedAcco.accoId,
                checkIn: $filter.checkInDate,
                checkOut: $filter.checkOutDate,
                totalGuests: $filter.guestCount,
                totalPrice: selectedAcco.totalPrice,
                reservationModal: true
            });
            handleReservationModalClicked();
        }
    }
</script>

<div class="flex flex-col h-screen">
    <header class="flex flex-col relative z-2 mb-10">
        <div id="logo" class="max-w-[1440px] mx-auto w-full flex items-center justify-between p-4 py-6 border-b relative">
            <a class="text-2xl font-semibold" href="/accommodations">Airdnb</a>
            <nav class="hidden md:flex items-center gap-[30px] lg:gap-6">
                <MiniSearchBar/>
            </nav>
                <div class="absolute right-[8rem]">
                    <a class="font-semibold text-airbnb-text-bold text-sm hover:cursor-pointer" href="/hosting">호스트 모드로 전환하기</a>
                </div>
                <div class="border border-gray-300 rounded-full flex gap-2 relative">
                    <button class="hidden md:flex place-items-center m-1 pl-3">
                        <i class="fa-solid fa-bars"></i>
                    </button>
                    <button class="md:hidden place-items-center m-1 px-3">
                        <i class="fa-solid fa-bars"></i>
                    </button>
                    <button class="hidden md:flex m-0.5" on:click={$isLoggedIn ? handleProfileBtnClick : handleLoginBtnClick}>
                        <img src="{profile_icon}" alt="User Profile Icon">
                    </button>
                    {#if isProfileBtnClicked && $isLoggedIn}
                        <ProfilePopup bind:isProfileBtnClicked="{isProfileBtnClicked}"/>
                    {/if}
                </div>
        </div>
    </header>
    <section class="max-w-[1440px] mx-auto w-full flex flex-1 min-h-screen">
        <section class="flex flex-col gap-3 min-w-[600px] w-[720px] overflow-scroll mx-2">
            {#each accommodations as acco}
                <AccoCard
                    accoId={acco.accoId}
                    placeCategory={acco.placeCategory}
                    title={acco.title}
                    maxGuestCount={acco.maxGuestCount}
                    bedroomCount={acco.bedroomCount}
                    bathroomCount={acco.bathroomCount}
                    totalPrice={acco.totalPrice}
                    on:accoSelected={handleAccoSelected}
                />
            {/each}
        </section>
        <section>
            <MapSection/>
        </section>
    </section>
    {#if isLoginBtnClicked && !$isLoggedIn}
        <LoginModal bind:isLoginBtnClicked="{isLoginBtnClicked}"/>
    {/if}
    {#if isReservationModalOpened}
        <ReservationModal
                title={$reservation.title}
                accoId={$reservation.accoId}
                checkIn={$reservation.checkIn}
                checkOut={$reservation.checkOut}
                totalGuests={$reservation.totalGuests}
                totalPrice={$reservation.totalPrice}
                on:click={handleReservationModalClicked}
                bind:isReservationModalOpened="{isReservationModalOpened}"/>
    {/if}
</div>