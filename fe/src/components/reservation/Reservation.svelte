<script>
  import { onMount } from "svelte";
  import { writable, get } from "svelte/store";
  import ReservationMessage from "./ReservationMessage.svelte";
  import ConfirmedTrips from "./ConfirmedTrips.svelte";
  import UpcomingTrips from "./UpcomingTrips.svelte";
  import CanceledTrips from "./CanceledTrips.svelte";
  import Modal from "./Modal.svelte";

  let API_BASE_URL = import.meta.env.VITE_API_BASE_URL;
  if (API_BASE_URL.endsWith('/')) {
    API_BASE_URL = API_BASE_URL.slice(0, -1);
  }

  let reservations = writable([]);
  let showModal = writable(false);
  let reservationToCancel = writable(null);

  onMount(async () => {
    const accessToken = localStorage.getItem("accessToken");
    const response = await fetch(`${API_BASE_URL}/reservation/member`, {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${accessToken}`
      }
    });
    const data = await response.json();
    reservations.set(data);
  });

  async function confirmReservation(reservationId) {
    const accessToken = localStorage.getItem("accessToken");
    const selectedReservation = get(reservations).find(r => r.reservationId === reservationId);
    const { accommodationName, fee, checkInDate, checkOutDate, capacity, length } = selectedReservation;

    try {
      const paymentId = `payment-${crypto.randomUUID()}`;
      const response = await PortOne.requestPayment({
        storeId: "store-9319fc39-3402-4252-bf2a-96485db653c8",
        channelKey: "channel-key-5e23687b-3fae-495a-92e0-a861d92fd571",
        paymentId: paymentId,
        orderName: accommodationName,
        totalAmount: selectedReservation.totalPrice,
        currency: "CURRENCY_KRW",
        payMethod: "CARD",
      });

      if (response.code != null) {
        // 오류 발생
        return alert(response.message);
      }

      // 결제 완료 후 서버에 결제 정보 전달
      const paymentVerificationResponse = await fetch(`${API_BASE_URL}/payment/complete`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${accessToken}`
        },
        body: JSON.stringify({
          paymentId: paymentId,
          reservationId: reservationId
        }),
      });

      if (!paymentVerificationResponse.ok) {
        const errorData = await paymentVerificationResponse.json();
        throw new Error(errorData.errorMessage);
      }

      // 결제와 예약 확정이 완료되면 예약 상태 업데이트
      reservations.update(current =>
        current.map(reservation =>
          reservation.reservationId === reservationId
            ? { ...reservation, isConfirmed: true }
            : reservation
        )
      );

      alert("예약이 확정되었습니다.");

    } catch (error) {
      console.error('Error making reservation:', error);
      alert('결제 요청에 실패했습니다.');
    }
  }

  async function cancelReservation() {
    const reservationId = get(reservationToCancel);
    if (!reservationId) return;

    const accessToken = localStorage.getItem("accessToken");
    const response = await fetch(`${API_BASE_URL}/reservation/${reservationId}`, {
      method: "DELETE",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${accessToken}`
      }
    });
    if (response.ok) {
      reservations.update(current =>
        current.map(reservation =>
          reservation.reservationId === reservationId
            ? { ...reservation, deleted: true }
            : reservation
        )
      );
      closeModal();
    } else {
      console.error("Failed to cancel reservation");
    }
  }

  function openModal(reservationId) {
    reservationToCancel.set(reservationId);
    showModal.set(true);
  }

  function closeModal() {
    reservationToCancel.set(null);
    showModal.set(false);
  }

  function hasNonDeletedReservations() {
    return get(reservations).some(reservation => !reservation.deleted);
  }
</script>

<div class="pt-28 container mx-auto">
  <h1 class="text-3xl font-bold my-8">여행</h1>
  <ReservationMessage {reservations} {hasNonDeletedReservations} />
  <ConfirmedTrips {reservations} />
  <UpcomingTrips {reservations} {confirmReservation} {openModal} />
  <CanceledTrips {reservations} />
  {#if $showModal}
    <Modal {closeModal} {cancelReservation} />
  {/if}
</div>

<style>
  /* Tailwind CSS 기본 설정을 사용하므로 별도 스타일 정의는 최소화 */
</style>
