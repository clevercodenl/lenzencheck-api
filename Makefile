# Start Docker containers
up:
	docker-compose -f docker-compose.yml up -d

# Remove Docker containers
stop:
	docker-compose -f docker-compose.yml stop

# Remove Docker containers
down:
	docker-compose -f docker-compose.yml down