document.querySelectorAll('.delete')
	.forEach(button =>  {
		button.addEventListener('click', (e) => {
			if(!confirm('本当に処理済みにしてよろしいですか？')) {
				e.preventDefault();
			}
		});
	});
