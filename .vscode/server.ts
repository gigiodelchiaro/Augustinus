
import { serve } from "https://deno.land/std/http/server.ts";
import { serveFile } from "https://deno.land/std/http/file_server.ts";
import { exists } from "https://deno.land/std/fs/mod.ts";

const port = 8000;

console.log(`Server listening on port ${port}`);

async function serveDirectory(dir: string): Promise<Response> {
  let body = "<h1>Index of " + dir + "</h1>";
  for await (const entry of Deno.readDir(dir)) {
    body += `<a href="${dir}/${entry.name}">${entry.name}</a><br>`;
  }
  return new Response(body, {
    headers: {
      "content-type": "text/html; charset=utf-8",
    },
  });
}

serve(async (req) => {
  const pathname = new URL(req.url).pathname;
  const filepath = `.${pathname}`;

  if (await exists(filepath)) {
    const fileInfo = await Deno.stat(filepath);
    if (fileInfo.isDirectory) {
      return serveDirectory(filepath);
    } else {
      try {
        return await serveFile(req, filepath);
      } catch {
        return new Response("404 Not Found", { status: 404 });
      }
    }
  } else {
    return new Response("404 Not Found", { status: 404 });
  }
}, { port });
