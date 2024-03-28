#Detached
#With Persistent Volume.  
#podman run -dt --name my-postgres -e POSTGRES_PASSWORD=password -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data" -p 5432:5432 postgres

#Without Volume.  All Data will disappear when
#Container comes down.
podman run -dt --name my-postgres -e POSTGRES_PASSWORD=password -p 5432:5432 postgres

#Interactive
#podman run -it --name my-postgres -e POSTGRES_PASSWORD=password -v "/otherhome/podman/postgres-podman/data:/var/lib/postgresql/data:Z" -p 5432:5432 postgres
