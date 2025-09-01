(function () {
    'use strict';
  
    /*** Game State ***/
    const state = {
      numPlayers: 3,
      target: 100,
      current: 0,
      round: 0,
      scores: [],
      playing: true,
      animating: false,
    };
  
    /*** DOM Elements ***/
    const el = {
      players: document.getElementById('players'),
      target: document.getElementById('target'),
      newGame: document.getElementById('newGame'),
      board: document.getElementById('board'),
      roll: document.getElementById('roll'),
      hold: document.getElementById('hold'),
      resetTurn: document.getElementById('resetTurn'),
      log: document.getElementById('log'),
      cube: document.getElementById('cube'),
      confetti: document.getElementById('confetti'),
      title: document.querySelector('h1'),
    };
  
    /*** Utils ***/
    const clamp = (v, min, max) => Math.max(min, Math.min(max, v));
    const randInt = (min, max) => Math.floor(Math.random() * (max - min + 1)) + min;
    const sleep = (ms) => new Promise((r) => setTimeout(r, ms));
  
    /*** Dice rotations (which face is up) ***/
    const faceRotations = {
      1: 'rotateX(0deg) rotateY(0deg)',
      2: 'rotateX(0deg) rotateY(-90deg)',
      3: 'rotateX(0deg) rotateY(180deg)',
      4: 'rotateX(0deg) rotateY(90deg)',
      5: 'rotateX(-90deg) rotateY(0deg)',
      6: 'rotateX(90deg) rotateY(0deg)',
    };
  
    function say(msg) {
      el.log.textContent = msg;
    }
  
    /*** Rendering ***/
    function renderBoard() {
      el.board.innerHTML = '';
      for (let i = 0; i < state.numPlayers; i++) {
        const card = document.createElement('article');
        card.className = 'player' + (i === state.current && state.playing ? ' active' : '');
        card.id = `p${i}`;
        card.innerHTML = `
          <header>
            <div class="name">Player ${i + 1}</div>
            ${i === state.current && state.playing ? '<span class="badge">Your Turn</span>' : ''}
          </header>
          <div class="scores" role="group" aria-label="Scores">
            <div class="pill" aria-label="Total score"><span class="label">Total</span> <span id="t${i}" style="margin-left:8px">${state.scores[i]}</span></div>
            <div class="pill" aria-label="Current turn score"><span class="label">Turn</span> <span id="c${i}" style="margin-left:8px">${i === state.current ? state.round : 0}</span></div>
          </div>
        `;
        el.board.appendChild(card);
      }
    }
  
    function updateActive() {
      [...document.querySelectorAll('.player')].forEach((p, idx) => {
        p.classList.toggle('active', idx === state.current && state.playing);
        const header = p.querySelector('header');
        const badge = header.querySelector('.badge');
        if (idx === state.current && state.playing) {
          if (!badge) {
            const b = document.createElement('span');
            b.className = 'badge';
            b.textContent = 'Your Turn';
            header.appendChild(b);
          }
        } else if (badge) {
          badge.remove();
        }
        const turnEl = p.querySelector(`#c${idx}`);
        if (turnEl) turnEl.textContent = idx === state.current ? state.round : 0;
      });
    }
  
    /*** Dice animation ***/
    function spinTo(value) {
      const extraX = 360 * randInt(1, 2);
      const extraY = 360 * randInt(1, 2);
      el.cube.style.transform = `translateZ(-1px) ${faceRotations[value]} rotateX(${extraX}deg) rotateY(${extraY}deg)`;
    }
  
    function animateAndResolveRoll() {
      return new Promise((resolve) => {
        const value = randInt(1, 6);
        const handler = () => {
          el.cube.removeEventListener('transitionend', handler);
          resolve(value);
        };
        el.cube.addEventListener('transitionend', handler, { once: true });
        spinTo(value);
      });
    }
  
    /*** Confetti ***/
    function burst() {
      const svg = el.confetti;
      const n = 140;
      svg.innerHTML = '';
      for (let i = 0; i < n; i++) {
        const x = Math.random() * 100;
        const s = 6 + Math.random() * 10;
        const hue = Math.floor(200 + Math.random() * 160);
        const rect = document.createElementNS('http://www.w3.org/2000/svg', 'rect');
        rect.setAttribute('x', x + 'vw');
        rect.setAttribute('y', '-10');
        rect.setAttribute('width', s);
        rect.setAttribute('height', s * 0.6);
        rect.setAttribute('fill', `hsl(${hue}, 90%, 60%)`);
        rect.style.animation = `fall ${6 + Math.random() * 6}s ${Math.random()}s cubic-bezier(.3,.7,.2,1) forwards`;
        svg.appendChild(rect);
      }
    }
  
    /*** Game flow ***/
    function updateButtons() {
      el.roll.disabled = !state.playing || state.animating;
      el.hold.disabled = !state.playing || state.round === 0 || state.animating;
      el.resetTurn.disabled = !state.playing || state.round === 0 || state.animating;
    }
  
    function nextPlayer() {
      state.current = (state.current + 1) % state.numPlayers;
      state.round = 0;
      updateActive();
      say(`Player ${state.current + 1}'s turn.`);
    }
  
    function init() {
      state.numPlayers = clamp(parseInt(el.players.value, 10) || 3, 3, 4);
      state.target = clamp(parseInt(el.target.value, 10) || 100, 20, 300);
      state.current = 0;
      state.round = 0;
      state.playing = true;
      state.animating = false;
      state.scores = new Array(state.numPlayers).fill(0);
      el.confetti.innerHTML = '';
      renderBoard();
      updateButtons();
      say(`Game on! First to ${state.target}. Player 1 starts.`);
      spinTo(6);
      el.title.textContent = `Dice Dash — ${state.numPlayers} Player`;
      // Remove any winner highlight if present (fresh render already handles this)
    }
  
    async function onRoll() {
      if (!state.playing || state.animating) return;
      state.animating = true;
      updateButtons();
  
      const value = await animateAndResolveRoll();
      state.animating = false;
  
      if (!state.playing) { updateButtons(); return; }
  
      if (value === 1) {
        state.round = 0;
        updateActive();
        say(`Oops! Player ${state.current + 1} rolled a 1. Turn lost.`);
        updateButtons();
        await sleep(350);
        nextPlayer();
        updateButtons();
      } else {
        state.round += value;
        const turnEl = document.getElementById(`c${state.current}`);
        if (turnEl) turnEl.textContent = state.round;
        say(`Player ${state.current + 1} rolled a ${value}. Turn: ${state.round}.`);
        updateButtons();
      }
    }
  
    async function onHold() {
      if (!state.playing || state.round === 0 || state.animating) return;
  
      state.scores[state.current] += state.round;
      const totalEl = document.getElementById(`t${state.current}`);
      if (totalEl) totalEl.textContent = state.scores[state.current];
  
      say(`Player ${state.current + 1} holds. +${state.round} points.`);
      state.round = 0;
      updateActive();
  
      if (state.scores[state.current] >= state.target) {
        state.playing = false;
        const winner = state.current;
        const card = document.getElementById(`p${winner}`);
        if (card) card.classList.add('winner');
        say(`🎉 Player ${winner + 1} wins! Total ${state.scores[winner]} points.`);
        burst();
        updateButtons();
        return;
      }
  
      await sleep(250);
      nextPlayer();
      updateButtons();
    }
  
    function onResetTurn() {
      if (!state.playing || state.round === 0 || state.animating) return;
      state.round = 0;
      updateActive();
      say(`Turn reset. Player ${state.current + 1}, roll again.`);
      updateButtons();
    }
  
    /*** Events ***/
    el.players.addEventListener('change', init);
    el.target.addEventListener('change', () => {
      const v = clamp(parseInt(el.target.value, 10) || 100, 20, 300);
      el.target.value = v;
      state.target = v;
      say(`Target set to ${v}.`);
    });
    el.newGame.addEventListener('click', init);
    el.roll.addEventListener('click', onRoll);
    el.hold.addEventListener('click', onHold);
    el.resetTurn.addEventListener('click', onResetTurn);
  
    window.addEventListener('keydown', (e) => {
      const tag = (e.target && e.target.tagName) || '';
      if (['INPUT', 'SELECT', 'TEXTAREA'].includes(tag)) return;
      if (e.key === ' ') { e.preventDefault(); onRoll(); }
      if (e.key === 'h' || e.key === 'H') onHold();
      if (e.key === 'n' || e.key === 'N') init();
    });
  
    /*** Kick off ***/
    init();
  })();
  
